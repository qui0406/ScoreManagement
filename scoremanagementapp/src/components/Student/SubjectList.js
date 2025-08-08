import { Container, Card, Row, Col, Alert, Form } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import { authApis, endpoints } from "../../configs/Apis";
import { useEffect, useState, useContext } from "react";
import MySpinner from "../layout/MySpinner";
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

    if (loading) return <MySpinner animation="border" />;
    if (msg) return <Alert variant="danger">{msg}</Alert>;

    return (
        <div className="container mt-5" style={{ maxWidth: 900, width: "100%" }}>
            <h4 className="text-center" style={{ color: "#3387c7", letterSpacing: 0.5, marginBottom:"50px"}}>Danh sách lớp môn </h4>
            <Row>
                {classes.map(classItem => (
                    <Col key={classItem.id} md={4} xs={12} className="mb-3">
                        <Card
                            className="w-80"
                            style={{
                                border: "1.5px solid #c9d7e7ff",borderRadius: 16,boxShadow: "0 2px 10px #a3c8eb22",
                                background: "#fafdff",minHeight: 100,transition: "box-shadow 0.15s, border 0.15s",
                            }}
                            onClick={() => nav(`/forumlist/${classItem.id}`)}
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
