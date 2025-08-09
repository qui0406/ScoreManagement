import { useEffect, useState, useContext } from "react";
import { useParams } from "react-router-dom";
import { authApis, endpoints } from "../../configs/Apis";
import { MyUserContext } from "../../configs/MyContexts";
import { Card, Alert, Spinner, Form, Button } from "react-bootstrap";

const ChatForum = () => {
    const { forumId } = useParams();
    const user = useContext(MyUserContext);
    const [replies, setReplies] = useState([]);
    const [loading, setLoading] = useState(false);
    const [msg, setMsg] = useState("");
    const [reply, setReply] = useState("");

    useEffect(() => {
        const loadReplies = async () => {
            setLoading(true);
            try {
                let res = await authApis().get(endpoints['getAllForums'](forumId));

                setReplies(res.data || []);
            } catch {
                setMsg("Không tải được nội dung chủ đề!");
            } finally {
                setLoading(false);
            }
        };
        loadReplies();
    }, [forumId]);

    const sendReply = async (e) => {
        e.preventDefault();
        try {
            const data = {
                forumId: forumId,
                message: reply,
                userResponseId: user?.id?.toString()
            };
            await authApis().post(endpoints['replyForum'](forumId), data);
            setReply("");
            const res = await authApis().get(endpoints['getAllForums'](forumId));
            setReplies(res.data || []);
        } catch {
            alert("Gửi phúc đáp thất bại!");
        }
    };

    const formatDateTime = (dateArr) => {
        if (!Array.isArray(dateArr) || dateArr.length < 6) return "";
        const [year, month, day, hour, minute, second] = dateArr;
        return `${day.toString().padStart(2, '0')}/${month.toString().padStart(2, '0')}/${year} ${hour}:${minute}:${second}`;
    };

    return (
        <div className="container mt-5" style={{ maxWidth: 650 }}>
            <Card>
                <Card.Header>
                    <b>Nội dung chủ đề</b>
                </Card.Header>
                <Card.Body>
                    <Form onSubmit={sendReply} className="mb-3">
                        <Form.Group>
                            <Form.Control
                                as="textarea"
                                placeholder="Nhập nội dung phúc đáp..."
                                value={reply}
                                onChange={e => setReply(e.target.value)}
                                rows={2}
                                required
                            />
                        </Form.Group>
                        <Button type="submit" variant="primary" className="mt-2" disabled={!reply.trim()}>
                            Gửi phúc đáp
                        </Button>
                    </Form>
                    {loading && <Spinner animation="border" />}
                    {msg && <Alert variant="danger">{msg}</Alert>}
                    <h6>Phúc đáp:</h6>
                    {replies.map((rep, idx) => {
                        const name = rep.student?.name || rep.teacher?.name || "Ẩn danh";
                        const content = rep.message;
                        const createdAt = formatDateTime(rep.createdAt);
                        return (
                            <div key={idx} style={{ background: "#f7f7f9", borderRadius: 10, marginBottom: 10, padding: 10 }}>
                                <b>{name}:</b> {content}
                                <div style={{ fontSize: 12, color: "#888" }}>{createdAt}</div>
                            </div>
                        );
                    })}
                    {replies.length === 0 && !loading && <div className="text-muted">Chưa có phúc đáp nào.</div>}
                </Card.Body>
            </Card>
        </div>
    );
};

export default ChatForum;
