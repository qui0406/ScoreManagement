import React, { useState } from "react";
import { FloatingLabel, Form, Button } from "react-bootstrap";
import { authApis, endpoints } from "../configs/Apis";
import Apis from "../configs/Apis";
import cookies from "react-cookies";
import { Link } from "react-router-dom";

const Login = () => {
    const info = [{
        label: "Tên đăng nhập",
        type: "text",
        field: "username",
    }, {
        label: "Mật khẩu",
        type: "password",
        field: "password",
    }]

    const [user, setUser] = useState({});
    const [loading, setLoading] = useState(false);
    const setState = (value, field) => {
        setUser({ ...user, [field]: value });
    };
    const login = async (e) => {
        e.preventDefault();
        let res = await Apis.post(endpoints['login'], {
            ...user
        }
        );
        cookies.save('token', res.data.token,);
        let userInfo = await authApis().get(endpoints['my-profile']);
        console.log(userInfo);
        console.log(res);
    }

    return (
        <>
            <div className="container mt-5">
                <h1>Đăng nhập</h1>
                <Form onSubmit={login}>
                    {info.map(f => <FloatingLabel key={f.field} controlId="floatingInput" label={f.label} className="mb-3">
                        <Form.Control type={f.type} placeholder={f.label} />
                    </FloatingLabel>)}
                    <Button variant="primary" type="submit">Đăng nhập</Button>
                </Form>
                <div className="mt-3">
                    Bạn chưa có tài khoản? <Link to="/register">Đăng ký</Link>
                </div>
            </div>
        </>

    );
}

export default Login;
