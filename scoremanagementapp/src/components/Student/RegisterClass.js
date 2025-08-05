import { use } from "react";
import { Container } from "react-bootstrap";

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

    return(
        <Container className="mt-5">
            <h2>Đăng ký môn học</h2>
            {msg && <Alert variant="info">{msg}</Alert>}
            </Container>
    )
};
export default RegisterClass;