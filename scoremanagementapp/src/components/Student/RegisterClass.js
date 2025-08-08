import { Container, Table, Alert, Button } from "react-bootstrap";
import { authApis, endpoints } from "../../configs/Apis";
import { useEffect, useState, useContext } from "react";
import { MyUserContext } from "../../configs/MyContexts";
import MySpinner from "../layout/MySpinner";
const RegisterClass = () => {
    const [loading, setLoading] = useState(false);
    const [allClasses, setAllClasses] = useState([]);
    const [registeredClasses, setRegisteredClasses] = useState([]);
    const [msg, setMsg] = useState("");
    const user = useContext(MyUserContext);

    useEffect(() => {
        const loadAllClasses = async () => {
            setLoading(true);
            try {
                let res = await authApis().get(endpoints['get-all-class-details']);
                setAllClasses(res.data || []);
            } catch (err) {
                setMsg("Lỗi tải danh sách lớp học");
            } finally {
                setLoading(false);
            }
        }
        loadAllClasses();
    }, []);

    useEffect(() => {
        const loadRegistered = async () => {
            try {
                let res = await authApis().get(endpoints['get-all-my-class']);
                setRegisteredClasses(res.data || []);
            } catch {
                setRegisteredClasses([]);
            }
        };
        loadRegistered();
    }, []);

    const registerClass = async (classId) => {
        setLoading(true);
        setMsg("");
        try {
            await authApis().post(endpoints['registerClass'], {
                studentId: user.id,
                classDetailId: classId,
                semesterId: 1
            });
            setMsg("Đăng ký thành công!");
            let res = await authApis().get(endpoints['get-all-my-class']);
            setRegisteredClasses(res.data || []);
        } catch {
            setMsg("Đăng ký không thành công!");
        } finally {
            setLoading(false);
        }
    };

    const isRegistered = (classId) =>
        registeredClasses.some((cls) => cls.id === classId);

    return (
        <Container className="mt-5">
            <h2>Đăng ký lớp học</h2>
            {msg && <Alert variant="info">{msg}</Alert>}
            {loading ? <MySpinner animation="border" /> : (
                <Table striped bordered hover>
                    <thead>
                        <tr>
                            <th>Mã lớp</th>
                            <th>Tên lớp</th>
                            <th>Môn học</th>
                            <th>Số lượng</th>
                            <th>Đăng ký</th>
                        </tr>
                    </thead>
                    <tbody>
                        {allClasses.length === 0 ? (
                            <tr><td colSpan={5}>Chưa có lớp học nào</td></tr>
                        ) : (
                            allClasses.map(classItem => (
                                <tr key={classItem.id}>
                                    <td>{classItem.classroom?.id}</td>
                                    <td>{classItem.classroom?.name}</td>
                                    <td>{classItem.subject?.subjectName}</td>
                                    <td>{classItem.totalStudents}</td>
                                    <td>
                                        <Button
                                            variant="primary"
                                            disabled={loading || isRegistered(classItem.id)}
                                            onClick={() => registerClass(classItem.id)}
                                        >
                                            {isRegistered(classItem.id) ? "Đã đăng ký" : "Đăng ký"}
                                        </Button>
                                    </td>
                                </tr>
                            ))
                        )}
                    </tbody>
                </Table>
            )}

            <h3 className="mt-4">Lớp đã đăng ký</h3>
            <Table striped bordered hover>
                <thead>
                    <tr>
                        <th>Mã lớp</th>
                        <th>Tên lớp</th>
                        <th>Môn học</th>
                    </tr>
                </thead>
                <tbody>
                    {registeredClasses.length === 0 ? (
                        <tr><td colSpan={3}>Chưa có lớp học nào</td></tr>
                    ) : (
                        registeredClasses.map(cls => (
                            <tr key={cls.id}>
                                <td>{cls.classroom?.id}</td>
                                <td>{cls.classroom?.name}</td>
                                <td>{cls.subject?.subjectName}</td>
                            </tr>
                        ))
                    )}
                </tbody>
            </Table>
        </Container>
    );
};
export default RegisterClass;
