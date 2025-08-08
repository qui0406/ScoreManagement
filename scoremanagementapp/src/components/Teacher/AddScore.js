import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import { Modal, Button, Form, Container } from "react-bootstrap";
import { authApis, endpoints } from "../../configs/Apis";
import { Table, Alert } from "react-bootstrap";
import MySpinner from "../layout/MySpinner";
import cookie from "react-cookies";

const AddScore = () => {
    const [loading, setLoading] = useState(false);
    const [loadingSave, setLoadingSave] = useState(false);
    const [loadingExport, setLoadingExport] = useState(false);
    const [loadingBlock, setLoadingBlock] = useState(false);
    const [loadingAddCol, setLoadingAddCol] = useState(false);

    const [msg, setMsg] = useState("");
    const { classSubjectId } = useParams();

    const [scoreTypes, setScoreTypes] = useState([]);
    const [allScoreTypes, setAllScoreTypes] = useState([]);
    const [selectedScoreTypeId, setSelectedScoreTypeId] = useState("");
    const [showAddCol, setShowAddCol] = useState(false);

    const [students, setStudents] = useState([]);
    const [scores, setScores] = useState({});

    const [isClose, setIsClose] = useState(false);

    const [selectedFile, setSelectedFile] = useState(null);

    const [q, setQ] = useState("");
    const [searching, setSearching] = useState(false);
    const [searchResults, setSearchResults] = useState([]);


    useEffect(() => {
        const loadStudents = async () => {
            setLoading(true);
            try {
                const res = await authApis().get(endpoints['getExportScores'](classSubjectId));
                const studentsData = [];
                const scoresData = {};

                res.data.forEach(item => {
                    studentsData.push({
                        id: item.student.id,
                        mssv: item.student.mssv,
                        name: item.student.name,
                    });
                    scoresData[item.student.id] = {};
                    (item.scores || []).forEach(type => {
                        scoresData[item.student.id][type.id] = type.scores[0] || "";
                    });
                });
                setStudents(studentsData);
                setScores(scoresData);

                const getScoreTypes = await authApis().get(endpoints['getScoreTypes'](classSubjectId));

                let scoreTypeArr = [];
                if (getScoreTypes.data.length > 0) {
                    scoreTypeArr = getScoreTypes.data.map(type => ({
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
        loadStudents();
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

    const addScoreTypes = async (e) => {
        e.preventDefault();
        if (!selectedScoreTypeId) {
            alert("Bạn phải chọn loại điểm!");
            return;
        }

        setLoading(true);
        try {
            await authApis().post(
                endpoints['addScoreType'](classSubjectId),
                { scoreTypeId: selectedScoreTypeId }
            ); 
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


    const addScore = (studentId, scoreTypeId, value) => {
        setScores(prev => ({
            ...prev,
            [studentId]: {
                ...prev[studentId],
                [scoreTypeId]: value
            }
        }));
    };



    const saveScore = async () => {
        setLoadingSave(true);
        console.log("Dữ liệu điểm trước khi lưu:", students);
        try {
            const payload = students.map(stu => ({
                studentId: stu.id,
                classDetailId: classSubjectId,
                scores: scores[stu.id] || {}
            }));
            console.log("Dữ liệu điểm trước khi gửi:", payload);
            await authApis().post(endpoints['addScore'], payload);
            alert("Lưu nháp thành công!");
        } catch (err) {
            alert("Lỗi khi lưu điểm!");
        } finally {
            setLoadingSave(false);
        }
    };


    const exportScore = async () => {
        setLoadingExport(true);
        try {
            const res = await authApis().post(
                endpoints['exportScore'](classSubjectId),
                {},
                { responseType: 'blob' }
            );
            const url = window.URL.createObjectURL(new Blob([res.data], { type: 'application/pdf' }));
            const link = document.createElement('a');
            link.href = url;
            link.setAttribute('download', `scores_${classSubjectId}.pdf`);
            document.body.appendChild(link);
            link.click();

            link.remove();
            setMsg("Xuất điểm thành công! File PDF đã được tải về.");
        } catch (err) {
            alert("Lỗi khi xuất điểm!");
        } finally {
            setLoadingExport(false);
        }
    };



    const blockScore = async () => {
        if (!window.confirm("Bạn có chắc chắn muốn đóng điểm? Sau khi đóng, không thể chỉnh sửa nữa!"))
            return;
        setLoadingBlock(true);
        try {
            const res = await authApis().post(endpoints['blockScore'](classSubjectId));

            if (res.status === 200) {
                alert("Đóng điểm thành công!");
                setIsClose(true);
            } else {
                alert("Lỗi khi đóng điểm!");
            }
        } catch (err) {
            alert("Lỗi khi đóng điểm!");
        } finally {
            setLoadingBlock(false);
        }
    };

    const uploadScoreFromCSV = async (e) => {
        const file = e.target.files[0];
        if (!file) {
            alert("Vui lòng chọn file CSV để tải lên!");
            return;
        }
        setSelectedFile(file);
        const formData = new FormData();
        formData.append("file", file);
        setLoading(true);
        try {
            const res = await authApis().post(endpoints['importScore'](classSubjectId), formData, {
                headers: {
                    "Content-Type": "multipart/form-data"
                }
            });
            setMsg("upload thành công!");
            window.location.reload();
        } catch (err) {
            console.error("Lỗi khi tải lên file CSV:", err);
        } finally {
            setLoading(false);
            setSelectedFile(null);
        }
    };

    //tìm kiếm sinh viên
    // useEffect(() => {
    //     if (!q || q.trim() === "") {
    //         setSearchResults([]);
    //         return;
    //     }
    //     const timer = setTimeout(() => {
    //         searchStudentScores(q);
    //     }, 500);
    //     return () => clearTimeout(timer);
    // }, [q]);

    const searchStudentScores = async (keyword) => {
        if (!keyword || !keyword.trim()) {
            setSearchResults([]);
            return;
        }
        let url;
        if (/^\d+$/.test(keyword.trim())) {
            url = endpoints['findExportListScoreBase'](classSubjectId) + `?mssv=${encodeURIComponent(keyword)}`;
        } else {
            url = endpoints['findExportListScoreBase'](classSubjectId) + `?fullName=${encodeURIComponent(keyword)}`;
        } setSearching(true);
        try {
            let res = await authApis().get(url);
            setSearchResults(res.data || []);
        } catch (err) {
            setSearchResults([]);
        } finally {
            setSearching(false);
        }
    };

    useEffect(() => {
        const showStatus = async () => {
            try {
                const res = await authApis().get(endpoints['statusScore'](classSubjectId));
                setIsClose(res.data === false);
            } catch {
                setIsClose(false);
            }
        };
        if (classSubjectId) showStatus();
    }, [classSubjectId]);

    return (
        < Container className="mt-5">

            <h2 className="text-center" style={{ color: "#3387c7", letterSpacing: 0.5, marginBottom: "50px" }} >Nhập điểm lớp học</h2>
            {msg && (
                <Alert
                    variant={msg.includes("Xuất điểm thành công") ? "success" : "danger"}
                >
                    {msg}
                </Alert>
            )}


            <div className="mb-3 d-flex align-items-center flex-wrap">
                <Button variant="success" className="me-2" onClick={saveScore} disabled={loading || isClose}>
                    {loadingSave ? <MySpinner size="sm" /> : null} Lưu nháp
                </Button>
                <Button variant="danger" onClick={blockScore} disabled={loading || isClose} className="me-2">
                    {loadingBlock ? <MySpinner size="sm" /> : null} Khóa điểm
                </Button>
                <Button variant="primary" onClick={exportScore} disabled={loading}>
                    {loadingExport ? <MySpinner size="sm" /> : null} Xuất điểm
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
                        >
                            <option value="">-- Chọn loại điểm --</option>
                            {allScoreTypes.map(type => (
                                <option key={type.id} value={type.id}>
                                    {type.scoreTypeName}
                                </option>
                            ))}
                        </Form.Select>
                        <Button variant="primary" type="submit" disabled={loading || isClose || !selectedScoreTypeId}>
                            {loading ? <MySpinner size="sm" /> : "Thêm"}
                        </Button>
                    </Form>
                )}
                <Form
                    onSubmit={e => {
                        e.preventDefault();
                        searchStudentScores(q);
                    }}
                    className="d-flex align-items-center ms-auto"
                >
                    <Form.Control
                        value={q}
                        onChange={e => setQ(e.target.value)}
                        type="text"
                        placeholder="Nhập MSSV hoặc họ tên sinh viên..."
                        style={{ width: 475, minWidth: 150, maxWidth: 475 }}
                    />
                    <Button
                        variant="primary"
                        className="ms-2"
                        onClick={() => searchStudentScores(q)}
                        disabled={loading || !q.trim()}
                    >Tìm</Button>
                </Form>
            </div>


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
                    {q && searchResults.length === 0 ? (
                        <tr>
                            <td colSpan={2 + scoreTypes.length} className="text-center">
                                Không tìm thấy sinh viên nào!
                            </td>
                        </tr>
                    ) : (
                        (q && searchResults.length > 0 ? searchResults : students).map(stu => (
                            <tr key={stu.student ? stu.student.mssv : stu.id}>
                                <td>{stu.student ? stu.student.mssv : stu.mssv}</td>
                                <td>{stu.student ? stu.student.name : stu.name}</td>
                                {scoreTypes.map(col => (
                                    <td key={col.id}>
                                        <input
                                            type="number"
                                            value={
                                                scores[stu.student ? stu.student.mssv : stu.id]?.[col.id] ?? ""
                                            }
                                            onChange={e => addScore(
                                                stu.student ? stu.student.mssv : stu.id,
                                                col.id,
                                                e.target.value
                                            )}
                                            style={{ width: 60 }}
                                            min={0}
                                            max={10}
                                            step={0.01}
                                            disabled={isClose}
                                        />
                                    </td>
                                ))}
                            </tr>
                        ))
                    )}
                </tbody>


            </Table>
            <input
                type="file" accept=".csv" style={{ display: "none" }} id="upload-score-csv" onChange={uploadScoreFromCSV}
            />
            <label htmlFor="upload-score-csv">
                <Button as="span" variant="warning" className="ms-2" disabled={loading || isClose}>
                    Nhập điểm từ CSV
                </Button>
            </label>


        </Container>
    );
};

export default AddScore;
