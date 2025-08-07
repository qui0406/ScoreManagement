import React, { useState, useContext } from "react";
import { FloatingLabel, Form, Button } from "react-bootstrap";
import { authApis, endpoints } from "../configs/Apis";
import Apis from "../configs/Apis";
import cookie from "react-cookies";
import { Link, useNavigate, useSearchParams } from "react-router-dom";
import { MyUserContext, MyDispatchContext    } from "./../configs/MyContexts";

const Login = () => {
    const info = [
        { label: "Tên đăng nhập", type: "text", field: "username" },
        { label: "Mật khẩu", type: "password", field: "password" }
    ];
    // const [,dispatch] = useContext(MyUserContext);
    const dispatch = useContext(MyDispatchContext);

    const [user, setUser] = useState({});
    const [loading, setLoading] = useState(false);
    const nav = useNavigate();
    const [q] = useSearchParams();
    const setState = (value, field) => { 
        setUser({ ...user, [field]: value });
    };
    const login = async (e) => {
        e.preventDefault();
        try {
            setLoading(true);
            let res = await Apis.post(endpoints['login'], { ...user });
            cookie.save('token', res.data.token);
            let userInfo = await authApis().get(endpoints['my-profile']);
            dispatch({
                "type": "login",
                "payload": userInfo.data
            });

            console.log("token", res.data.token);


            let next = q.get('next');
            nav(next ? next : '/home');

            console.log("Thanh cong")
        } catch (e) {
            console.error(e);
            alert("Đăng nhập thất bạiii");
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="container mt-5 d-flex justify-content-center">
        <div style={{ minWidth: 350, maxWidth: 400, width: "100%" }}>
            <h1 className="text-center mb-4">Đăng nhập</h1>
            <Form onSubmit={login}>
                {info.map(f =>
                    <FloatingLabel key={f.field} controlId={f.field} label={f.label} className="mb-3">
                        <Form.Control
                            type={f.type}
                            placeholder={f.label}
                            value={user[f.field] || ""}
                            onChange={e => setState(e.target.value, f.field)}
                            style={{ background: "#f7f7f9" }}
                        />
                    </FloatingLabel>
                )}
                <Button variant="primary" type="submit" disabled={loading} className="w-100 py-2 mt-1"
                        style={{ fontWeight: 400, fontSize: 17, letterSpacing: 1 }}>
                    {loading ? "Đang đăng nhập..." : "Đăng nhập"}
                </Button>
            </Form>
            <div className="mt-3 text-center">
                Bạn chưa có tài khoản? <Link to="/register" style={{ color: "#0d6efd", fontWeight: 400 }}>Đăng ký</Link>
            </div>
        </div>
    </div>
    );
};

export default Login;
