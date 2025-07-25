import { useEffect, useState, useContext } from "react";
import Apis from "../configs/Apis";
import { authApis, endpoints } from "../configs/Apis";
import { Row, Col, Card, Alert, Button } from "react-bootstrap";
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
                let url =  `${endpoints['products']}`;
                try {
                    setLoading(true);
                    let res = await Apis.get(url);
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
            <h2>Danh sách lớp học</h2>
            {(!classes || classes.length === 0) && <Alert variant="info">Không có lớp nào!</Alert>}
            <Row>
                {classes.map(c => (
                    <Col key={c.id} md={4} xs={12} className="mb-3">
                        <Card >
                            <Card.Body>
                                <Card.Title>
                                    {c.subject?.subjectName}
                                </Card.Title>
                                <Card.Text>
                                    <b>Lớp:</b> {c.classroom?.name}<br />
                                    <b>Học kỳ:</b> {c.semester}<br />
                                    <b>Số sinh viên:</b> {c.totalStudents}<br />
                                    <b>Số loại điểm:</b> {c.countScoreType}
                                </Card.Text>
                                <Button variant="info" onClick={() => nav(`/studentlist/${c.id}`)} className="cursor-pointer">Xem chi tiết</Button>
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