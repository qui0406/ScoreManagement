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
                let url =  `${endpoints['my-classes']}`;
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
        < Container className="mt-5">
            <h2 className="mb-4" >Danh sách lớp học</h2>
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
                                    <b>Học kỳ:</b> {c.semester?.name}<br />
                                    {/* <b>Số sinh viên:</b> {c.totalStudents}<br /> */}
                                    {/* <b>Số loại điểm:</b> {c.countScoreType} */}
                                </Card.Text>
                                <Button className="me-2" variant="info" onClick={() => nav(`/studentlist/${c.id}`)} >Xem chi tiết</Button>
                                <Button variant="primary" onClick={() => nav(`/addscore/${c.id}`)}>Nhập điểm</Button>
                            </Card.Body>
                        </Card>
                    </Col>
                ))}
            </Row>
        </Container>

    );
}
export default Home;
// import { useEffect, useState, useContext } from "react";
// import { authApis, endpoints } from "../configs/Apis";
// import { Row, Col, Card, Alert, Button, Container, Table, Spinner } from "react-bootstrap";
// import { MyUserContext } from "../configs/MyContexts";
// import { useNavigate } from "react-router-dom";

// const Home = () => {
//     const [classes, setClasses] = useState([]);
//     const [subjects, setSubjects] = useState([]);
//     const [loading, setLoading] = useState(false);
//     const [msg, setMsg] = useState("");
//     const user = useContext(MyUserContext);
//     const nav = useNavigate();

//     useEffect(() => {
//         if (!user) return;

//         const loadData = async () => {
//             setLoading(true);
//             setMsg("");
//             try {
//                 if (user.role === "ROLE_TEACHER") {
//                     // Nếu là giáo viên, lấy danh sách lớp
//                     let res = await authApis().get(endpoints['my-classes']);
//                     setClasses(res.data);
//                 } else {
//                     // Nếu là học sinh, lấy danh sách môn học
//                     let res = await authApis().get(endpoints['mySubjects']);
//                     setSubjects(res.data);
//                 }
//             } catch (error) {
//                 setMsg("Lỗi tải dữ liệu");
//             } finally {
//                 setLoading(false);
//             }
//         };
//         loadData();
//     }, [user]);

//     if (loading) return <Container className="mt-5"><Spinner animation="border" /></Container>;
//     if (!user) return null;

//     // === Nếu là giáo viên ===
//     if (user.role === "ROLE_TEACHER") {
//         return (
//             <Container className="mt-5">
//                 <h2 className="mb-4">Danh sách lớp học</h2>
//                 {msg && <Alert variant="danger">{msg}</Alert>}
//                 {(!classes || classes.length === 0) && <Alert variant="info">Không có lớp nào!</Alert>}
//                 <Row>
//                     {classes.map(c => (
//                         <Col key={c.id} md={4} xs={12} className="mb-3">
//                             <Card>
//                                 <Card.Body>
//                                     <Card.Title>
//                                         {c.subject?.subjectName}
//                                     </Card.Title>
//                                     <Card.Text>
//                                         <b>Lớp:</b> {c.classroom?.name}<br />
//                                         <b>Học kỳ:</b> {c.semester?.name}<br />
//                                     </Card.Text>
//                                     <Button className="me-2" variant="info" onClick={() => nav(`/studentlist/${c.id}`)}>Xem chi tiết</Button>
//                                     <Button variant="primary" onClick={() => nav(`/addscore/${c.id}`)}>Nhập điểm</Button>
//                                 </Card.Body>
//                             </Card>
//                         </Col>
//                     ))}
//                 </Row>
//             </Container>
//         );
//     }

//     // === Nếu là học sinh ===
//     return (
//         <Container className="mt-5">
//             <Card className="mb-4">
//                 <Card.Body>
//                     <Card.Title>📚 Danh sách môn học của bạn</Card.Title>
//                 </Card.Body>
//             </Card>

//             {msg && <Alert variant="danger">{msg}</Alert>}

//             <Table striped bordered hover>
//                 <thead>
//                     <tr>
//                         <th>STT</th>
//                         <th>Mã môn học</th>
//                         <th>Tên môn học</th>
//                     </tr>
//                 </thead>
//                 <tbody>
//                     {subjects.map((sub, idx) => (
//                         <tr key={sub.id}>
//                             <td>{idx + 1}</td>
//                             <td>{sub.id}</td>
//                             <td>{sub.subjectName}</td>
//                         </tr>
//                     ))}
//                 </tbody>
//             </Table>
//         </Container>
//     );
// };

// export default Home;
