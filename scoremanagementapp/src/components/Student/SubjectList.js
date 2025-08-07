import { Container, Card, Row, Col, Alert, Spinner, Form } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import { authApis, endpoints } from "../../configs/Apis";
import { useEffect, useState, useContext } from "react";
import { MyUserContext } from "../../configs/MyContexts";
const SubjectList = () => {
    const [classes, setClasses] = useState([]);
    const [loading, setLoading] = useState(false);
    const [msg, setMsg] = useState("");
    const nav = useNavigate();

    useEffect(() => {
        const load = async () => {
            setLoading(true);
            try {
                // Lấy tất cả lớp mà user có thể chat
                let res = await authApis().get(endpoints['get-all-my-class']);
                setClasses(res.data || []);
            } catch {
                setMsg("Không tải được danh sách lớp!");
            } finally {
                setLoading(false);
            }
        };
        load();
    }, []);

    if (loading) return <Spinner animation="border" />;
    if (msg) return <Alert variant="danger">{msg}</Alert>;

    return (
        <div className="container mt-5" style={{ maxWidth: 900, width: "100%" }}>
            <h4 className="mb-4">💬 Danh sách lớp môn </h4>
            <Row>
                {classes.map(classItem => (
                    <Col key={classItem.id} md={4} xs={12} className="mb-3">
                        <Card
                            style={{ cursor: "pointer" }}
                            onClick={() => nav(`/chatbox/${classItem.id}`)}
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

        </div>
    );
};
export default SubjectList;
