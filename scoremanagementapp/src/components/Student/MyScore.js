import React, { useEffect, useState, useContext } from "react";
import { useParams } from "react-router-dom";
import { authApis, endpoints } from "../configs/Apis";
import { Card, Container, Spinner, Alert, Table } from "react-bootstrap";
import { MyUserContext } from "../configs/MyContexts";

const MyScore = () => {
    const { classSubjectId } = useParams();
    const user = useContext(MyUserContext);
    const [scoreData, setScoreData] = useState(null);
    const [loading, setLoading] = useState(false);
    const [msg, setMsg] = useState("");

    useEffect(() => {
        const loadScore = async () => {
            if (!user || !user.id || !classSubjectId) return;
            setLoading(true);
            setMsg("");
            try {
                let res = await authApis().get(endpoints.getMyScore(user.id, classSubjectId));
                setScoreData(res.data);
            } catch (error) {
                setMsg("Không thể tải điểm.");
            } finally {
                setLoading(false);
            }
        };
        loadScore();
    }, [user, classSubjectId]);

    if (loading) return <Container className="mt-5"><Spinner animation="border" /></Container>;

    return (
        <Container className="mt-5">
            <Card className="mb-4">
                <Card.Body>
                    <Card.Title>📝 Bảng điểm chi tiết</Card.Title>
                    <Card.Text>
                        {scoreData && (
                            <>
                                <b>MSSV:</b> {scoreData.student?.mssv} <br />
                                <b>Họ tên:</b> {scoreData.student?.name} <br />
                                <b>Lớp:</b> {scoreData.classroom?.name} <br />
                                <b>Môn học:</b> {scoreData.subject?.subjectName} <br />
                                <b>Giáo viên:</b> {scoreData.teacher?.name} ({scoreData.teacher?.msgv})
                            </>
                        )}
                    </Card.Text>
                </Card.Body>
            </Card>
            {msg && <Alert variant="danger">{msg}</Alert>}
            {scoreData && (
                <Table striped bordered>
                    <thead>
                        <tr>
                            <th>Loại điểm</th>
                            <th colSpan={4}>Các lần chấm</th>
                        </tr>
                    </thead>
                    <tbody>
                        {scoreData.scores && scoreData.scores.length > 0 ? (
                            scoreData.scores.map((item, idx) => (
                                <tr key={item.id || idx}>
                                    <td>{item.scoreTypeName}</td>
                                    {item.scores && item.scores.length > 0 ? (
                                        item.scores.map((val, i) => (
                                            <td key={i}>{val}</td>
                                        ))
                                    ) : (
                                        <td colSpan={4}>Chưa có điểm</td>
                                    )}
                                </tr>
                            ))
                        ) : (
                            <tr>
                                <td colSpan={5}>Không có dữ liệu điểm.</td>
                            </tr>
                        )}
                    </tbody>
                </Table>
            )}
            {!scoreData && !msg && <Alert variant="info">Không có dữ liệu điểm.</Alert>}
        </Container>
    );
};

export default MyScore;
