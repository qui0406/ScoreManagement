import { useEffect, useState } from "react";
import { Container, Card, Row, Col, Alert, Spinner, Form } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import { authApis, endpoints } from "../../configs/Apis";
import { MyUserContext } from "../../configs/MyContexts";
import { useContext } from "react";
const MyClasses = () => {
    const [classes, setClasses] = useState([]);
    const [loading, setLoading] = useState(true);
    const [msg, setMsg] = useState(null);
    const { user } = useContext(MyUserContext);
    const [semesterId, setSemesterId] = useState("");
    const nav = useNavigate();

    //tạm thời khi chưa có api lấy danh sách học kỳ
    // const [allSemesters, setAllSemesters] = useState([]);

    const [allSemesters, setAllSemesters] = useState([
        { id: "1", name: "HK1 2024-2025" },
        { id: "2", name: "HK2 2024-2025" }
    ]);
    useEffect(() => {
        if (!semesterId) {
            setClasses([]);
            return;
        }
        const loadClasses = async () => {
            setLoading(true);
            try {
                const response = await authApis().get(endpoints['get-all-my-class']);
                // const listSemesters = await authApis().get(endpoints['semesters']);
                // setAllSemesters(listSemesters.data || []);
                // console.log("Danh sách học kỳ:", listSemesters.data);
                setClasses(response.data);
            } catch (error) {
                setMsg("Lỗi tải danh sách lớp học");
            } finally {
                setLoading(false);
            }
        };

        loadClasses();
    }, [semesterId]);

    return (
        <Container className="mt-5">
            <h2 className="text-center" style={{ color: "#3387c7", letterSpacing: 0.5, marginBottom:"50px"}}>Danh sách lớp học</h2>

            <Form.Group className="mb-4" style={{ maxWidth: 250 }}>
                <Form.Label>Chọn học kỳ</Form.Label>
                <Form.Select
                    value={semesterId}
                    onChange={e => setSemesterId(e.target.value)}
                >
                    <option value="">-- Chọn học kỳ --</option>
                    {allSemesters.map(sem => (
                        <option key={sem.id} value={sem.id}>{sem.name}</option>
                    ))}
                </Form.Select>
            </Form.Group>

            {/* {loading ? <Spinner animation="border" /> : null} */}
            {msg && <Alert variant="danger">{msg}</Alert>}
            {(!classes || classes.length === 0) && !loading && (
                <Alert variant="info">Không có lớp học nào!</Alert>
            )}

            <Row>
                {classes.map(classItem => (
                    <Col key={classItem.id} md={4} xs={12} className="mb-3">
                        <Card
                            onClick={() => nav(`/myscore/${classItem.id}`)}
                            className="w-80"
                            style={{
                                border: "1.5px solid #c9d7e7ff",borderRadius: 16,boxShadow: "0 2px 10px #a3c8eb22",
                                background: "#fafdff",minHeight: 100,transition: "box-shadow 0.15s, border 0.15s",
                            }}
                        >
                            <Card.Body>
                                <Card.Title>
                                    {classItem.classroom?.name}
                                </Card.Title>
                                <Card.Text>
                                    <b>Mã lớp:</b> {classItem.classroom?.id}<br />
                                    <b>Môn học:</b> {classItem.subject?.subjectName}<br />
                                </Card.Text>
                            </Card.Body>
                        </Card>
                    </Col>
                ))}
            </Row>
        </Container>
    );
}

export default MyClasses;