import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { authApis, endpoints } from "../../configs/Apis";
import { Table, Alert } from "react-bootstrap";

const StudentList = () => {
    const { classId } = useParams();
    const [students, setStudents] = useState([]);
    const [loading, setLoading] = useState(false);
    const [msg, setMsg] = useState("");

    useEffect(() => {
        const loadStudents = async () => {
            setLoading(true);
            try {
                let res = await authApis().get(endpoints['class-students'](classId));
                setStudents(res.data);
            } catch (err) {
                setMsg("Lỗi tải danh sách sinh viên");
            } finally {
                setLoading(false);
            }
        };
        loadStudents();
    }, [classId]);

    const genderToText = g => g == null ? "" : g ? "Nam" : "Nữ";

    return (
        <>
            <h2>Danh sách sinh viên</h2>
            {msg && <Alert variant="danger">{msg}</Alert>}
            <Table striped bordered hover>
                <thead>
                    <tr>
                        <th>Mã SV</th>
                        <th>Họ</th>
                        <th>Tên</th>
                        <th>Email</th>
                        <th>Phone</th>
                        <th>Giới tính</th>
                        <th>Ngày sinh</th>
                        <th>Lớp</th>
                    </tr>
                </thead>
                <tbody>
                    {students.map(stu => (
                        <tr key={stu.id}>
                            <td>{stu.studentCode}</td>
                            <td>{stu.lastName}</td>
                            <td>{stu.firstName}</td>
                            <td>{stu.email}</td>
                            <td>{stu.phone}</td>
                            <td>{genderToText(stu.gender)}</td>
                            <td>{stu.dob}</td>
                            <td>{stu.classroomName}</td>
                        </tr>
                    ))}
                </tbody>
            </Table>
        </>
    );
};

export default StudentList;
