// import { useEffect, useState } from "react";
// import { authApis, endpoints } from "../configs/Apis";
// import { ListGroup, Spinner, Alert } from "react-bootstrap";
// import { useNavigate } from "react-router-dom";

// const ChatRoomList = () => {
//     const [classes, setClasses] = useState([]);
//     const [loading, setLoading] = useState(false);
//     const [msg, setMsg] = useState("");
//     const nav = useNavigate();

//     useEffect(() => {
//         const load = async () => {
//             setLoading(true);
//             try {
//                 // Lấy tất cả lớp mà user có thể chat
//                 let res = await authApis().get(endpoints['get-all-my-class']);
//                 setClasses(res.data || []);
//             } catch {
//                 setMsg("Không tải được danh sách lớp!");
//             } finally {
//                 setLoading(false);
//             }
//         };
//         load();
//     }, []);

//     if (loading) return <Spinner animation="border" />;
//     if (msg) return <Alert variant="danger">{msg}</Alert>;

//     return (
//         <div className="container mt-5">
//             <h4 className="mb-4">💬 Chọn lớp để trao đổi</h4>
//             <ListGroup>
//                 {classes.map(cls => (
//                     <ListGroup.Item
//                         action
//                         key={cls.id}
//                         onClick={() => nav(`/chat/${cls.id}`)}
//                     >
//                         {cls.classroom?.name || cls.subject?.subjectName}
//                     </ListGroup.Item>
//                 ))}
//             </ListGroup>
//         </div>
//     );
// };
// export default ChatRoomList;
