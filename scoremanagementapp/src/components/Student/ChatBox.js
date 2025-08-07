import { useEffect, useState, useRef, useContext } from "react";
import { useParams } from "react-router-dom";
import { authApis, endpoints } from "../configs/Apis";
import { MyUserContext } from "../configs/MyContexts";
import { Form, Button, Card, Spinner, Alert,ListGroup } from "react-bootstrap";

const ChatBox = () => {
    const { classDetailId } = useParams();
    const user = useContext(MyUserContext);
    const [forumList, setForumList] = useState([]);
    const [input, setInput] = useState("");
    const [loading, setLoading] = useState(false);
    const [msg, setMsg] = useState("");


      useEffect(() => {
        const loadForums = async () => {
            setLoading(true);
            try {
                let res = await authApis().get(`/get-all-forum/${classDetailId}`);
                setForumList(res.data || []);
            } catch {
                setMsg("Không thể tải danh sách chủ đề!");
            } finally {
                setLoading(false);
            }
        };
        loadForums();
    }, [classDetailId]);


    

    return (
        <div className="container mt-5" style={{ maxWidth: 650 }}>
            <Card>
                <Card.Header>
                    <b>Diễn đàn lớp {classDetailId}</b>
                </Card.Header>
                {loading && <Spinner animation="border" />}
            {msg && <Alert variant="danger">{msg}</Alert>}
            <ListGroup>
                {forumList.map(forum => (
                    <ListGroup.Item
                        key={forum.id}
                        action
                        onClick={() => nav(`/forum/${classDetailId}/${forum.id}`)}
                    >
                        <b>{forum.title}</b> <span className="text-muted">({forum.createdBy?.name})</span>
                        <div style={{ fontSize: 13, color: "#888" }}>{forum.content}</div>
                    </ListGroup.Item>
                ))}
            </ListGroup>
                
            </Card>
        </div>
    );
};
export default ChatBox;
