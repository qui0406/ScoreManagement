import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import { Modal, Button, Form } from "react-bootstrap";
import { authApis, endpoints } from "../../configs/Apis";
import { Table, Alert, Spinner } from "react-bootstrap";

const AddScore = () => {
    const [loading, setLoading] = useState(false);
    const [msg, setMsg] = useState("");
    const { classId } = useParams();
    const [scoreTypes, setScoreTypes] = useState([]);
    const [boxScoreColumn, setBoxScoreColumn] = useState(true);
    const [numCols, setNumCols] = useState(0);
    const [students, setStudents] = useState([]);
    const [scores, setScores] = useState({}); 

    // Lấy danh sách sinh viên khi đã đóng modal chọn cột điểm
    useEffect(() => {
        if (!boxScoreColumn) {
            const fetchData = async () => {
                setLoading(true);
                try {
                    let resStu = await authApis().get(endpoints['studentList'](classId));
                    setStudents(resStu.data);
                    let resCol = await authApis().get(endpoints['getScoreTypes'](classId));
                    setScoreTypes(resCol.data); 
                } catch (err) {
                    setMsg("Lỗi không tìm thấy học sinh/cột điểm!");
                } finally {
                    setLoading(false);
                }
            };
            fetchData();
        }
    }, [classId, boxScoreColumn]);

    // Thêm cột điểm 
    const addScoreTypes = async (e) => {
        e.preventDefault();
        if (numCols > 5 || numCols < 0) {
            alert("Số cột phải từ 0 đến 5!");
            return;
        }
        setLoading(true);
        try {
            for (let i = 1; i <= numCols; i++) {
                await authApis().post(
                    endpoints['addScoreType'](classId),
                    { name: `Điểm khác ${i}` } 
                );
            }
            setBoxScoreColumn(false);
        } catch (err) {
            alert("Lỗi khi thêm loại điểm mới!");
        } finally {
            setLoading(false);
        }
    };

    const modalClose = () => setBoxScoreColumn(false);

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
            alert("Lưu điểm thành công!");
        } catch (err) {
            alert("Lỗi khi lưu điểm!");
        } finally {
            setLoading(false);
        }
    };

    return (
        <>
            {boxScoreColumn && (
                <div className="modal show" style={{ display: 'block', position: 'initial' }}>
                    <Modal.Dialog>
                        <Modal.Header closeButton onHide={modalClose}>
                            <Modal.Title>Chọn số cột điểm muốn thêm</Modal.Title>
                        </Modal.Header>
                        <Form onSubmit={addScoreTypes}>
                            <Modal.Body>
                                <p>Bạn muốn thêm bao nhiêu cột điểm (Tối đa 5 cột)?</p>
                                <Form.Control
                                    type="number"
                                    min={0}
                                    max={5}
                                    value={numCols}
                                    onChange={e => setNumCols(Number(e.target.value))}
                                />
                            </Modal.Body>
                            <Modal.Footer>
                                <Button variant="secondary" onClick={modalClose}>Đóng</Button>
                                <Button variant="primary" type="submit" disabled={loading}>
                                    {loading ? <Spinner size="sm" /> : "Xác nhận"}
                                </Button>
                            </Modal.Footer>
                        </Form>
                    </Modal.Dialog>
                </div>
            )}
            {!boxScoreColumn && (
                <>
                    <h2>Nhập điểm lớp học</h2>
                    {msg && <Alert variant="danger">{msg}</Alert>}
                    <Button
                        variant="success"
                        className="mb-3"
                        onClick={saveScore}
                        disabled={loading}
                    >
                        {loading ? <Spinner size="sm" /> : null} Lưu tất cả điểm
                    </Button>
                    <Table striped bordered hover>
                        <thead>
                            <tr>
                                <th>Mã SV</th>
                                <th>Họ</th>
                                <th>Tên</th>
                                {scoreTypes.map(col => (
                                    <th key={col.key || col.id}>{col.label || col.name}</th>
                                ))}
                            </tr>
                        </thead>
                        <tbody>
                            {students.map(stu => (
                                <tr key={stu.id}>
                                    <td>{stu.studentCode}</td>
                                    <td>{stu.lastName}</td>
                                    <td>{stu.firstName}</td>
                                    {scoreTypes.map(col => (
                                        <td key={col.key || col.id}>
                                            <input
                                                type="number"
                                                value={scores[stu.id]?.[col.key || col.id] || ""}
                                                onChange={e => addScore(stu.id, col.key || col.id, e.target.value)}
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
                </>
            )}
        </>
    );
};

export default AddScore;
