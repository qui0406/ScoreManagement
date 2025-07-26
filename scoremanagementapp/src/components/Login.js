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
        <div className="container mt-5">
            <h1>Đăng nhập</h1>
            <Form onSubmit={login}>
                {info.map(f =>
                    <FloatingLabel key={f.field} controlId="floatingInput" label={f.label} className="mb-3">
                        <Form.Control
                            type={f.type}
                            placeholder={f.label}
                            value={user[f.field] || ""}
                            onChange={e => setState(e.target.value, f.field)}
                        />
                    </FloatingLabel>
                )}
                <Button variant="primary" type="submit" disabled={loading}>
                    {loading ? "Đang đăng nhập..." : "Đăng nhập"}
                </Button>
            </Form>
            <div className="mt-3">
                Bạn chưa có tài khoản? <Link to="/register">Đăng ký</Link>
            </div>
        </div>
    );
};

export default Login;
