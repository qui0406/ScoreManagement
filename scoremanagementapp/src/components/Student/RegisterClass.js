import { use } from "react";
import { Container, Tab, Table, Alert,Button ,Spinner} from "react-bootstrap";
import { authApis, endpoints } from "../../configs/Apis";
import { useEffect, useState, useContext } from "react";
import MySpinner from "../layout/MySpinner";
const RegisterClass = () => {
    const [loading, setLoading] = useState(false);
    const [subjects, setSubjects] = useState([]);
    const [msg, setMsg] = useState("");
    const [semesterId, setSemesterId] = useState("");

    useEffect(() => {
        const loadSubjects = async () => {
            setLoading(true);
            try {
                let res = await authApis().get(endpoints['getClassesInSemester'](semesterId));
                setSubjects(res.data);
            } catch (err) {
                console.error("Lỗi khi gọi API:", err.response?.status, err.response?.data);
                setMsg("Lỗi tải danh sách môn học");
            } finally {
                setLoading(false);
            }
        }
        loadSubjects();
    }, [semesterId]);


    const registerClass = async (subjectId) => {
        setLoading(true);
        try {
            await authApis().post(endpoints['registerClass'], {
                classId: subjectId
            });
            setMsg("Đăng ký thành công!");
            let res = await authApis().get(endpoints['getClassesInSemester'](semesterId));
            setMySubjects(res.data || []);
        } catch {
            setMsg("Đăng ký không thành công!");
        } finally {
            setLoading(false);
        }
    };

    return (
        <Container className="mt-5">
            <h2>Đăng ký môn học</h2>
            {msg && <Alert variant="info">{msg}</Alert>}
            {loading ? <Spinner animation="border" /> : (
                <Table striped bordered hover>
                    <thead>
                        <tr>
                            <th>Mã lớp</th>
                            <th>Tên lớp/môn học</th>
                            <th>Giảng viên</th>
                            <th>Thời gian</th>
                            <th>Đăng ký</th>
                        </tr>
                    </thead>
                    <tbody>
                        {subjects.length === 0 ? (
                            <tr><th>Chưa có môn học nào</th></tr>
                        ) : (
                            subjects.map(subject => (
                                <tr>
                                    <td>{subject.id}</td>
                                    <td>{subject.subjectName}</td>
                                    <td>{subject.teacher?.name}</td>
                                    <td>{subject.schedule}</td>
                                    <td>
                                        <Button
                                            variant="primary"
                                            onClick={() => registerClass(subject.id)}
                                            disabled={loading}
                                        >
                                            Đăng ký
                                        </Button>
                                    </td>
                                </tr>
                            ))
                        )}
                    </tbody>
                </Table>
            )}
        </Container>
    )
};
export default RegisterClass;