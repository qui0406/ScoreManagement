import { useEffect, useState, useContext } from "react";
import { useParams } from "react-router-dom";
import { authApis } from "../../configs/Apis";
import { MyUserContext } from "../../configs/MyContexts";
import { Card, Alert, Spinner, Form, Button } from "react-bootstrap";

const ChatForum = () => {
    const { classDetailId, forumId } = useParams();
    const user = useContext(MyUserContext);
    const [replies, setReplies] = useState([]);
    const [loading, setLoading] = useState(false);
    const [msg, setMsg] = useState("");
    const [reply, setReply] = useState("");

    useEffect(() => {
        const loadReplies = async () => {
            setLoading(true);
            try {
                let res = await authApis().get(['getAllForums'](forumId));
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
            await authApis().post(['replyForum'](forumId), { content: reply });
            setReply("");
        } catch {
            alert("Gửi bình luận thất bại!");
        }
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
                                placeholder="Nhập nội dung bình luận..."
                                value={reply}
                                onChange={e => setReply(e.target.value)}
                                rows={2}
                                required
                            />
                        </Form.Group>
                        <Button type="submit" variant="primary" className="mt-2" disabled={!reply.trim()}>
                            Gửi bình luận
                        </Button>
                    </Form>
                    {loading && <Spinner animation="border" />}
                    {msg && <Alert variant="danger">{msg}</Alert>}
                    <h6>Bình luận:</h6>
                    {replies.map(rep => (
                        <div key={rep.id} style={{ background: "#f7f7f9", borderRadius: 10, marginBottom: 10, padding: 10 }}>
                            <b>{rep.createdBy?.name || "Ẩn danh"}:</b> {rep.content}
                            <div style={{ fontSize: 12, color: "#888" }}>
                                {new Date(rep.createdAt).toLocaleString()}
                            </div>
                        </div>
                    ))}
                    {replies.length === 0 && !loading && <div className="text-muted">Chưa có bình luận nào.</div>}
                </Card.Body>
            </Card>
        </div>
    );
};
export default ChatForum;
