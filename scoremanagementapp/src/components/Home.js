import { useEffect, useState, useContext } from "react";
import Apis from "../configs/Apis";
import { authApis, endpoints } from "../configs/Apis";
import { Row, Col, Card, Alert, Button, Container } from "react-bootstrap";
import { MyUserContext } from "../configs/MyContexts";
import { useNavigate } from "react-router-dom";

const Home = () => {
    const [classes, setClasses] = useState([]);
    const [loading, setLoading] = useState(false);
    const [msg, setMsg] = useState("");
    const user = useContext(MyUserContext);
    const nav = useNavigate();
    useEffect(() => {
            // if (!user || user.role !== 'teacher')
            //     nav("/login");
            const loadClasses = async () => {
                let url =  `${endpoints['mySubjects']}`;
                try {
                    setLoading(true);
                    let res = await authApis().get(url);
                    console.log("🔎 data từ API:", res.data);

                    setClasses(res.data);
                } catch (error) {
                    setMsg("Lỗi tải danh sách lớp học");
                } finally {
                    setLoading(false);
                }
            };
            loadClasses();
        }, [user]
    );
    return (
        <>
        {(!classes || classes.length === 0) && <Alert variant="info">Không có lớp nào!</Alert>}
        <Row>
            {classes.map(c => (
                <Col key={c.id} md={4} xs={12} className="mb-3">
                <Card>
                    <Card.Body>
                    <Card.Title>{c.classes?.subjectName}</Card.Title>
                    <Card.Text>
                        <b>Lớp:</b> {c.classes?.subjectName}<br />
                        <b>Học kỳ:</b> {c.semester?.name}<br />
                    </Card.Text>
                    <Button className="me-2" variant="info" onClick={() => nav(`/studentlist/${c.id}`)}>Xem chi tiết</Button>
                    <Button variant="primary" onClick={() => nav(`/addscore/${c.id}`)}>Nhập điểm</Button>
                    </Card.Body>
                </Card>
                </Col>
            ))}
        </Row>
        </>
        
    );
}
export default Home;
