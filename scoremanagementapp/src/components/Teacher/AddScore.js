import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import { Modal, Button, Form, Container } from "react-bootstrap";
import { authApis, endpoints } from "../../configs/Apis";
import { Table, Alert, Spinner } from "react-bootstrap";

const AddScore = () => {
    const [loading, setLoading] = useState(false);
    const [msg, setMsg] = useState("");
    const { classId } = useParams();
    const [scoreTypes, setScoreTypes] = useState([]);
    const [students, setStudents] = useState([]);
    const [scores, setScores] = useState({});
    const [showAddCol, setShowAddCol] = useState(false);
    const [newColName, setNewColName] = useState("");
    const [isClose, setIsClose] = useState(false);

    // Lấy danh sách sinh viên khi đã đóng modal chọn cột điểm
    useEffect(() => {
        const loadContent = async () => {
            setLoading(true);
            try {
                let res = await authApis().get(endpoints['studentList'](classId));
                setStudents(res.data);
                console.log("🔎 Dữ liệu sinh viên:", res.data);

                let resCol = await authApis().get(endpoints['getScoreTypes'](classId));
                setScoreTypes(resCol.data);
                console.log("Cột điểm từ API:", resCol.data);

            } catch (err) {
                setMsg("Lỗi không tìm thấy học sinh/cột điểm!");
            } finally {
                setLoading(false);
            }
        };
        loadContent();

    }, [classId]);

    // Thêm cột điểm 
    const addScoreTypes = async (e) => {
        e.preventDefault();
        if (!newColName.trim()) {
            alert("Tên loại điểm không được để trống!");
            return;
        }
        if (scoreTypes.length >= 5) {
            alert("Tối đa chỉ được thêm 5 loại điểm!");
            return;
        }
        setLoading(true);
        try {
            await authApis().post(
                endpoints['addScoreType'](classId),
                { name: newColName }
            );
            setNewColName("");
            setShowAddCol(false);
            let resCol = await authApis().get(endpoints['getScoreTypes'](classId));
            setScoreTypes(resCol.data);

        } catch (err) {
            alert("Lỗi khi thêm loại điểm mới!");
        } finally {
            setLoading(false);
        }
    };


    // Cập nhật điểm từng ô
    const addScore = (studentId, key, value) => {
        setScores(prev => ({
            ...prev,
            [studentId]: {
                ...prev[studentId],
                [key]: value
            }
        }));
    };

    // Lưu tất cả điểm
    const saveScore = async () => {
        setLoading(true);
        try {
            const payload = students.map(stu => ({
                studentId: stu.id,
                classSubjectId: classId,
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
            const res = await authApis().post(endpoints['closeScore'], { classSubjectId: classId });
            if (res.status === 200) {
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
                        <Form.Control
                            type="text"
                            placeholder="Tên loại điểm mới"
                            value={newColName}
                            onChange={e => setNewColName(e.target.value)}
                            className="me-2"
                            disabled={isClose}
                        />
                        <Button variant="primary" type="submit" disabled={loading || isClose}>
                            {loading ? <Spinner size="sm" /> : "Thêm"}
                        </Button>
                    </Form>
                )}
            </div>
            <Table striped bordered hover>
                <thead>
                    <tr>
                        <th>Email SV</th>
                        <th>Họ</th>
                        <th>Tên</th>
                        {scoreTypes.map(col => (
                            <th key={col.id}>{col.name}</th>
                        ))}
                    </tr>
                </thead>
                <tbody>
                    {students.map(stu => (
                        <tr key={stu.id}>
                            <td>{stu.email}</td>
                            <td>{stu.lastName}</td>
                            <td>{stu.firstName}</td>
                            {scoreTypes.map(col => (
                                <td key={col.key || col.id}>
                                    <input
                                        type="number"
                                        value={scores[stu.id]?.[col.id] || ""}
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
