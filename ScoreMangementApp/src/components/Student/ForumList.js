import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { authApis, endpoints } from "../../configs/Apis";
import { Card, Spinner, Alert, ListGroup } from "react-bootstrap";
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

    const formatDateTime = (dateArr) => {
        if (!Array.isArray(dateArr) || dateArr.length < 6) return "";
        const [year, month, day, hour, minute, second] = dateArr;
        return `${day.toString().padStart(2, '0')}/${month.toString().padStart(2, '0')}/${year} ${hour}:${minute}:${second}`;
    };

    return (
        <div className="container mt-5" style={{ maxWidth: 650 }}>
            <Card>
                <Card.Header>
                    <b>Diễn đàn lớp {classDetailId}</b>
                </Card.Header>
                {loading && <MySpinner animation="border" />}
                {msg && <Alert variant="danger">{msg}</Alert>}
                <ListGroup>
                    {forumList.map((forum, index) => {
                        const createdBy = forum.studentCreatedId?.name || forum.teacherCreatedId?.name || "Không rõ người tạo";
                        const createdTime = formatDateTime(forum.createdAt);

                        return (
                            <ListGroup.Item
                                key={index}
                                action
                                onClick={() => nav(`/chatforum/${forum.id}`)} 
                            >
                                <b>{forum.content}</b> <span className="text-muted">({createdBy})</span>
                                <div style={{ fontSize: 13, color: "#888" }}>{createdTime}</div>
                            </ListGroup.Item>
                        );
                    })}
                </ListGroup>
            </Card>
        </div>
    );
};

export default ChatBox;
