import { useEffect, useState, useRef, useContext } from "react";
import { useParams } from "react-router-dom";
import { authApis, endpoints } from "../../configs/Apis";
import { Card, Spinner, Alert, ListGroup } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import MySpinner from "../layout/MySpinner";

const ChatBox = () => {
    const { classDetailId } = useParams();
    const [forumList, setForumList] = useState([]);
    const [loading, setLoading] = useState(false);
    const [msg, setMsg] = useState("");
    const nav = useNavigate();

    useEffect(() => {
        const loadForums = async () => {
            setLoading(true);
            try {
                let res = await authApis().get(endpoints['get-all-forum'](classDetailId));
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
                {loading && <MySpinner animation="border" />}
                {msg && <Alert variant="danger">{msg}</Alert>}
                <ListGroup>
                    {forumList.map(forum => (
                        <ListGroup.Item
                            key={forum.id}
                            action
                            onClick={() => nav(`/chatforum/${classDetailId}/${forum.id}`)}
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
