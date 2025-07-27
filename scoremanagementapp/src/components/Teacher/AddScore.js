import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import { Modal, Button, Form, Container } from "react-bootstrap";
import { authApis, endpoints } from "../../configs/Apis";
import { Table, Alert, Spinner } from "react-bootstrap";
import cookie from "react-cookies";

const AddScore = () => {
    const [loading, setLoading] = useState(false);
    const [msg, setMsg] = useState("");
    const { classSubjectId } = useParams();

    const [scoreTypes, setScoreTypes] = useState([]);
    const [allScoreTypes, setAllScoreTypes] = useState([]);
    const [selectedScoreTypeId, setSelectedScoreTypeId] = useState("");
    const [showAddCol, setShowAddCol] = useState(false);

    const [students, setStudents] = useState([]);
    const [scores, setScores] = useState({});

    const [isClose, setIsClose] = useState(false);


    // const [q, setQ] = useState(""); 
    // const [searching, setSearching] = useState(false);
    // const [searchResults, setSearchResults] = useState([]);


    useEffect(() => {
        const fetchData = async () => {
            console.log("Token FE:", cookie.load('token'));
            console.log("Headers:", authApis().defaults.headers);

            setLoading(true);
            try {
                const res = await authApis().get(endpoints['getExportScores'](classSubjectId));
                const studentsData = [];
                const scoresData = {};

                res.data.forEach(item => {
                    studentsData.push({
                        mssv: item.student.mssv,
                        name: item.student.name,
                        id: item.student.mssv, 
                    });
                    // scores
                    scoresData[item.student.mssv] = {};
                    (item.scores || []).forEach(type => {
                        scoresData[item.student.mssv][type.id] = type.scores[0] || "";
                    });
                });
                setStudents(studentsData);
                setScores(scoresData);

                let scoreTypeArr = [];
                if (res.data.length > 0) {
                    scoreTypeArr = res.data[0].scores.map(type => ({
                        id: type.id,
                        scoreTypeName: type.scoreTypeName
                    }));
                }
                setScoreTypes(scoreTypeArr);

                setMsg("");
            } catch (err) {
                setMsg("Không thể tải dữ liệu sinh viên và điểm!");
            } finally {
                setLoading(false);
            }
        };
        fetchData();
    }, [classSubjectId]);


    useEffect(() => {
        const loadAllScoreTypes = async () => {
            try {
                let res = await authApis().get(endpoints['allScoreTypes']);
                setAllScoreTypes(res.data);
            } catch {
                setAllScoreTypes([]);
            }
        };
        loadAllScoreTypes();
    }, []);

    // Thêm cột điểm 
    const addScoreTypes = async (e) => {
        e.preventDefault();
        if (!selectedScoreTypeId) {
            alert("Bạn phải chọn loại điểm!");
            return;
        }

        setLoading(true);
        try {
            await authApis().post(endpoints['addScoreType'](classSubjectId, selectedScoreTypeId));
            setSelectedScoreTypeId("");
            setShowAddCol(false);

            let resCol = await authApis().get(endpoints['getScoreTypes'](classSubjectId));
            setScoreTypes(resCol.data);
        } catch (err) {
            alert("Lỗi khi thêm loại điểm mới!");
        } finally {
            setLoading(false);
        }
    };



    // // Cập nhật điểm từng ô
    // const addScore = (studentId, key, value) => {
    //     setScores(prev => ({
    //         ...prev,
    //         [studentId]: {
    //             ...prev[studentId],
    //             [key]: value
    //         }
    //     }));
    // };

    const addScore = (studentId, scoreTypeId, value) => {
        setScores(prev => ({
            ...prev,
            [studentId]: {
                ...prev[studentId],
                [scoreTypeId]: value
            }
        }));
    };


    // Lưu tất cả điểm

    const saveScore = async () => {
        setLoading(true);
        try {
            const payload = students.map(stu => ({
                studentId: stu.id,
                classSubjectId: classSubjectId,
                scores: scores[stu.id] || {}
            }));
            await authApis().post(endpoints['addScore'], payload);
            alert("Lưu nháp thành công!");
        } catch (err) {
            alert("Lỗi khi lưu điểm!");
        } finally {
            setLoading(false);
        }
    };




    const closeScore = async () => {
        if (!window.confirm("Bạn có chắc chắn muốn đóng điểm? Sau khi đóng, không thể chỉnh sửa nữa!"))
            return;
        setLoading(true);
        try {
            const res = await authApis().post(endpoints['closeScore'](classSubjectId));

            if (res.status === 200 && res.data === true) {
                setIsClose(true);
                alert("Đóng điểm thành công!");
            } else {
                alert("Lỗi khi đóng điểm!");
            }
        } catch (err) {
            alert("Lỗi khi đóng điểm!");
        } finally {
            setLoading(false);
        }
    };
    // useEffect(() => {
    //     if (!q || q.trim() === "") {
    //         setSearchResults([]);
    //         return;
    //     }
    //     setSearching(true);
    //     const timer = setTimeout(() => {
    //         searchStudentScores(q);
    //     }, 500);
    //     return () => clearTimeout(timer);
    // }, [q]);

    // const searchStudentScores = async (keyword) => {
    //     let url = endpoints['findExportListScoreBase'](classSubjectId) + `?mssv=${encodeURIComponent(keyword)}&fullName=${encodeURIComponent(keyword)}`;
    //     try {
    //         let res = await authApis().get(url);
    //         setSearchResults(res.data || []);
    //     } catch (err) {
    //         setSearchResults([]);
    //     } finally {
    //         setSearching(false);
    //     }
    // };

    return (
        < Container className="mt-5">

            <h2>Nhập điểm lớp học</h2>
            {msg && <Alert variant="danger">{msg}</Alert>}


            <div className="mb-3 d-flex align-items-center">
                <Button
                    variant="success"
                    className="me-2"
                    onClick={saveScore}
                    disabled={loading || isClose}
                >
                    {loading ? <Spinner size="sm" /> : null} Lưu nháp
                </Button>

                <Button
                    variant="danger"
                    onClick={closeScore}
                    disabled={loading || isClose}
                >
                    {loading ? <Spinner size="sm" /> : null} Khóa điểm
                </Button>

                <Button variant="info" onClick={() => setShowAddCol(!showAddCol)} className="ms-2" disabled={isClose}>
                    Thêm cột loại điểm
                </Button>

                {showAddCol && (
                    <Form onSubmit={addScoreTypes} className="d-flex align-items-center ms-2">
                        <Form.Select
                            className="me-2"
                            value={selectedScoreTypeId}
                            onChange={e => setSelectedScoreTypeId(e.target.value)}
                            disabled={isClose}
                        >
                            <option value="">-- Chọn loại điểm --</option>
                            {allScoreTypes.map(type => (
                                <option key={type.id} value={type.id}>
                                    {type.scoreTypeName}
                                </option>
                            ))}
                        </Form.Select>

                        <Button variant="primary" type="submit" disabled={loading || isClose || !selectedScoreTypeId}>
                            {loading ? <Spinner size="sm" /> : "Thêm"}
                        </Button>
                    </Form>

                )}


            </div>
            {/* <Form > 
                <Form.Group className="mb-3 mt-2">
                    <Form.Control
                        value={q}
                        onChange={e => setQ(e.target.value)}
                        type="text"
                        placeholder="Tìm kiếm theo MSSV, họ hoặc tên sinh viên..."
                    />
                </Form.Group>
            </Form> */}
            <Table striped bordered hover>
                <thead>
                    <tr>
                        <th>MSSV</th>
                        <th>Họ tên</th>
                        {scoreTypes.map(col => (
                            <th key={col.id}>{col.scoreTypeName}</th>
                        ))}
                    </tr>
                </thead>
                <tbody>
                    {students.map(stu => (
                        <tr key={stu.id}>
                            <td>{stu.mssv}</td>
                            <td>{stu.name}</td>
                            {scoreTypes.map(col => (
                                <td key={col.id}>
                                    <input
                                        type="number"
                                        value={scores[stu.id]?.[col.id] ?? ""}
                                        onChange={e => addScore(stu.id, col.id, e.target.value)}
                                        style={{ width: 60 }}
                                        min={0}
                                        max={10}
                                        step={0.01}
                                    />
                                </td>
                            ))}
                        </tr>
                    ))}
                </tbody>
            </Table>


        </Container>
    );
};

export default AddScore;
