import { authApis, endpoints } from "../../configs/Apis";
import { useEffect, useState, useContext } from "react";
import { Container, Card, Row, Col, Alert } from "react-bootstrap";

const SubjectList = () => {
    const [subjects, setSubjects] = useState([]);
    const [loading, setLoading] = useState(true);
    const [msg, setMsg] = useState(null);

    useEffect(() => {
        const loadSubject = async () => {
            try {
                const response = await authApis().get(endpoints['mySubjects']);
                setSubjects(response.data);
            } catch (msg) {
                setMsg(msg.message || "Failed to fetch subjects");
            } finally {
                setLoading(false);
            }
        };

        loadSubject();
    }, []);

    if (loading) return <div>Loading...</div>;

    return (
    <Container className="mt-5">
        <Card className="mb-4">
            <Card.Body>
                <Card.Title>📚 Danh sách môn học của bạn</Card.Title>
            </Card.Body>
        </Card>

        {msg && <Alert variant="danger">{msg}</Alert>}

        {(!subjects || subjects.length === 0) && <Alert variant="info">Không có môn học nào!</Alert>}
        <Row>
            {subjects.map((sub, idx) => (
                <Col key={sub.id} md={4} xs={12} className="mb-3">
                    <Card>
                        <Card.Body>
                            <Card.Title>{sub.subjectName}</Card.Title>
                            <Card.Text>
                                <b>Mã môn học:</b> {sub.id}<br />
                                <b>Tên môn học:</b> {sub.subjectName}<br />

                            </Card.Text>
                        </Card.Body>
                    </Card>
                </Col>
            ))}
        </Row>
    </Container>
);
}
export default SubjectList;