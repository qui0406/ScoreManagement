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
                                    {user.role === "Teacher" ?
                                        <>
                                            <Link to="/home" className="nav-link">Trang chủ</Link>
                                            {/* <Link to="" className="nav-link"></Link> */}
                                            {/* <Link to="" className="nav-link"></Link> */}
                                        </>
                                        : user.role === "Student" ?
                                            <>
                                                <Link to="/student/home" className="nav-link">Trang chủ</Link>
                                                <Link to="/student/my-courses" className="nav-link">Khóa học của tôi</Link>
                                                <Link to="/student/profile" className="nav-link">Hồ sơ</Link>
                                            </>
                                            : null}

                                    <Button className="btn btn-danger ms-2" onClick={() => dispatch({ type: "logout" })}>
                                        Đăng xuất
                                    </Button>
                                </>}
                            <Nav.Link href="#" disabled>
                                Link
                            </Nav.Link>
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