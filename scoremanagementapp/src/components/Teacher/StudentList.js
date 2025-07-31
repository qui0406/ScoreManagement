import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { authApis, endpoints } from "../../configs/Apis";
import { Table, Alert, Container, Card, Col, Row } from "react-bootstrap";

const StudentList = () => {
    const { classId } = useParams();
    const [students, setStudents] = useState([]);
    const [loading, setLoading] = useState(false);
    const [msg, setMsg] = useState("");
    console.log("Token hiện tại:", document.cookie);
    const [classDetails, setClassDetails] = useState(null);
    useEffect(() => {

        const loadStudents = async () => {
            setLoading(true);
            try {
                let res = await authApis().get(endpoints['studentList'](classId));
                console.log("Gọi URL:", endpoints['studentList'](classId));
                setStudents(res.data);
                let resInfo = await authApis().get(endpoints['classDetails'](classId));
                setClassDetails(resInfo.data);
            } catch (err) {
                console.error(" Lỗi khi gọi API:", err.response?.status, err.response?.data);

                setMsg("Lỗi tải danh sách sinh viên");
            } finally {
                setLoading(false);
            }
        };
        loadStudents();
    }, [classId]);

    const genderToText = g => g == null ? "" : g ? "Nam" : "Nữ";

    return (
        <Container className="mt-5">
            {msg && <Alert variant="danger">{msg}</Alert>}
            {classDetails && (
                <Card className="mb-4">
                    <Card.Body>
                        <Card.Title>📘 Thông tin lớp học</Card.Title>
                        <Row>
                            <Col md={4}><b>Môn học:</b> {classDetails.subject?.subjectName}</Col>
                            <Col md={4}><b>Lớp:</b> {classDetails.classroom?.name}</Col>
                            <Col md={4}><b>Học kỳ:</b> {classDetails.semester?.name}</Col>
                        </Row>
                    </Card.Body>
                </Card>
            )}
            <h3 style={{ color: "#9ec5fe" }}>Danh sách sinh viên</h3>

            <Table striped bordered hover>
                <thead>
                    <tr>
                        <th>Mã SV</th>
                        <th>Họ</th>
                        <th>Tên</th>
                        <th>Email</th>
                        <th>Khóa</th>
                        <th>Phone</th>
                        <th>Giới tính</th>
                    </tr>
                </thead>
                <tbody>
                    {students.map(stu => (
                        <tr key={stu.id}>
                            <td>{stu.mssv}</td>
                            <td>{stu.lastName}</td>
                            <td>{stu.firstName}</td>
                            <td>{stu.email}</td>
                            <td>{stu.schoolYear ? new Date(stu.schoolYear).getFullYear() : ''}</td>
                            <td>{stu.phone}</td>
                            <td>{genderToText(stu.gender)}</td>
                        </tr>
                    ))}
                </tbody>
            </Table>
        </Container>
    );
};

export default StudentList;
