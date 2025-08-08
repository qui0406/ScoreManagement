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
            let url = `${endpoints['my-classes']}`;
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
        <Container className="mt-5" >
            <h2 className="text-center" style={{ color: "#3387c7", letterSpacing: 0.5, marginBottom:"50px"}} >Danh sách lớp học </h2>
            {(!classes || classes.length === 0) && <Alert variant="info">Không có lớp nào!</Alert>}
            <Row>
                {classes.map(c => (
                    <Col key={c.id} md={4} xs={12} className="mb-3">
                        <Card className="w-80"
                            style={{
                                border: "1.5px solid #c9d7e7ff",borderRadius: 16,boxShadow: "0 2px 10px #a3c8eb22",
                                background: "#fafdff",minHeight: 100,transition: "box-shadow 0.15s, border 0.15s",
                            }}>
                            <Card.Body>
                                <Card.Title>{c.subjectName}</Card.Title>
                                <Card.Text>
                                    <b>Lớp:</b> {c.name}<br />
                                </Card.Text>
                                <div className=" justify-content-end gap-2">
                                    <Button variant="outline-primary"
                                        style={{
                                            borderWidth: 2, background: "#fff",color: "#3387c7",borderColor: "#b1d2ef",marginRight:"10px"}}
                                        onClick={() => nav(`/studentlist/${c.id}`)}>
                                        Xem chi tiết</Button>
                                    <Button style={{background: "#3387c7", color: "#fff",}}
                                        onClick={() => nav(`/addscore/${c.id}`)}>
                                        Nhập điểm</Button>
                                </div>
                            </Card.Body>
                        </Card>
                    </Col>
                ))}
            </Row>
        </Container>

    );
}
export default Home;
