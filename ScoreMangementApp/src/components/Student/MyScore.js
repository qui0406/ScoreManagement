import React, { useEffect, useState, useContext } from "react";
import { useParams } from "react-router-dom";
import { authApis, endpoints } from "../../configs/Apis";
import { MyUserContext } from "../../configs/MyContexts";
import { Card, Container, Alert, Table, Row, Col } from "react-bootstrap";
import MySpinner from "../layout/MySpinner";
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
                let res = await authApis().get(endpoints["my-score"](classSubjectId));
                setScoreData(res.data);
                console.log("🔎 Điểm của bạn:", res.data);
            } catch (error) {
                setMsg("Không thể tải điểm.");
            } finally {
                setLoading(false);
            }
        };
        loadScore();
    }, [user, classSubjectId]);

    if (loading)
        return (
            <Container className="mt-5 text-center">
                <MySpinner animation="border" variant="primary" />
            </Container>
        );

    return (
        <Container className="mt-5">
            <Card
                className="mb-4 shadow-sm border-0 mx-auto"
                style={{ backgroundColor: "#ecf7ffff", borderRadius: "14px", maxWidth: "700px" }}>
                <Card.Body>
                    <Card.Title
                        style={{ color: "#3387c7", fontWeight: "600", borderBottom: "1px solid #cfe4f9", paddingBottom: "8px", }}
                        className="text-center">
                        Thông tin chi tiết
                    </Card.Title>

                    {scoreData && (
                        <Row className="mt-3" 
                            style={{ fontSize: "16px", lineHeight: "1.8" }}>
                            <Col style={{paddingLeft:"50px"}} sm={6}><b>MSSV:</b> {scoreData.student?.mssv}</Col>
                            <Col style={{paddingLeft:"70px"}} sm={6}><b>Họ tên:</b> {scoreData.student?.name}</Col>
                            <Col style={{paddingLeft:"50px"}} sm={6}><b>Lớp:</b> {scoreData.classroom?.name}</Col>
                            <Col style={{paddingLeft:"70px"}} sm={6}><b>Môn học:</b> {scoreData.subject?.subjectName}</Col>
                            <Col style={{paddingLeft:"50px"}} sm={12}><b>Giáo viên:</b> {scoreData.teacher?.name} ({scoreData.teacher?.msgv})</Col>
                        </Row>
                    )}
                </Card.Body>
            </Card>

            <h3 style={{ color: "#3387c7", fontWeight: "600", paddingBottom: "8px", marginBottom: "20px", }} className="text-center">
                Bảng điểm môn học
            </h3>

            {msg && <Alert variant="danger">{msg}</Alert>}

            {scoreData && (
                <Table
                    striped
                    bordered
                    style={{
                        backgroundColor: "#ffffff", borderRadius: "10px", overflow: "hidden",
                    }}>
                    <thead style={{ backgroundColor: "#e3f2fd", color: "#3387c7", fontWeight: "bold", textAlign: "center", }}>
                        <tr>
                            <th>Loại điểm</th>
                            <th colSpan={4}>Số điểm</th>
                        </tr>
                    </thead>
                    <tbody>
                        {scoreData.scores && scoreData.scores.length > 0 ? (
                            scoreData.scores.map((item, idx) => (
                                <tr key={idx}>
                                    <td><b>{item.scoreTypeName}</b></td>
                                    {item.scores && item.scores.length > 0 ? (
                                        item.scores.map((val, i) => (
                                            <td key={i}>{val}</td>
                                        ))
                                    ) : (
                                        <td colSpan={4} className="text-center text-muted">Chưa có điểm</td>
                                    )}
                                </tr>
                            ))
                        ) : (
                            <tr>
                                <td colSpan={5} className="text-center">Không có dữ liệu điểm.</td>
                            </tr>
                        )}
                    </tbody>
                </Table>
            )}

            {!scoreData && !msg && (
                <Alert variant="info" className="text-center">
                    Không có dữ liệu điểm.
                </Alert>
            )}
        </Container>
    );
};

export default MyScore;
