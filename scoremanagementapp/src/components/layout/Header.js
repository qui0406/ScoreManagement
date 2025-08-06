import { useContext } from "react";
import { Navbar, Container, Nav, NavDropdown, Form, Button } from "react-bootstrap";
import { MyUserContext, MyDispatchContext } from "../../configs/MyContexts";
import { Link } from "react-router-dom";

const Header = () => {
    const user = useContext(MyUserContext);
    const dispatch = useContext(MyDispatchContext);
    return (
        <>
            <Navbar expand="lg" className="bg-body-tertiary">
                <Container fluid>
                    <Navbar.Brand href="#">Score Management</Navbar.Brand>
                    <Navbar.Toggle aria-controls="navbarScroll" />
                    <Navbar.Collapse id="navbarScroll">
                        <Nav
                            className="me-auto my-2 my-lg-0"
                            style={{ maxHeight: '100px' }}
                            navbarScroll
                        >
                            {user === null ? <>
                                <Link to="/login" className="nav-link">Đăng nhập</Link>
                                <Link to="/register" className="nav-link">Đăng ký</Link>
                            </> :
                                <>
                                    {user.role === "ROLE_TEACHER" ?
                                        <>
                                            <Link to="/home" className="nav-link">Trang chủ</Link>
                                            {/* <Link to="" className="nav-link"></Link> */}
                                            {/* <Link to="" className="nav-link"></Link> */}
                                        </>
                                        : user.role === "ROLE_USER" ?
                                            <>
                                                <Link to="/student/home" className="nav-link">Trang chủ</Link>
                                                <Link to="/subjects" className="nav-link">Khóa học</Link>
                                                <Link to="/myclasses" className="nav-link">Lớp học</Link>

                                                <Link to="/registerclass" className="nav-link">Đăng kí môn học</Link>

                                                <Link to="/student/profile" className="nav-link">Hồ sơ</Link>
                                                <Link to="/student/chat" className="nav-link">Chat</Link>

                                            </>
                                            : null}


                                    <Link to="/profile" className="nav-link text-info">
                                        <img src={user.avatar} width={30} className="rounded" />
                                        <span className="ms-2">{user.username}!</span>
                                    </Link>
                                    <Button className="btn btn-danger ms-2" onClick={() => dispatch({ type: "logout" })}>
                                        Đăng xuất
                                    </Button>
                                </>}
                            {/* <Nav.Link href="#" disabled>
                                Link
                            </Nav.Link> */}
                        </Nav>
                        <Form className="d-flex">
                            <Form.Control
                                type="search"
                                placeholder="Search"
                                className="me-2"
                                aria-label="Search"
                            />
                            <Button variant="outline-success">Search</Button>
                        </Form>
                    </Navbar.Collapse>
                </Container>
            </Navbar>
        </>
    );
}
export default Header;