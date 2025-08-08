import { Container } from "react-bootstrap";

const Footer = () => {
    return (
        <footer
            style={{
                background: "#f8f9fa",
                borderTop: "1px solid #dde7f5",
                padding: "16px 0",
                width: "100%",
                fontSize: 17,
                // marginTop: "50px"
            }}
        >
            <Container className="text-center">
                <span style={{ fontWeight: 600, color: "#0d6efd", fontSize: 18 }}>
                    Score Management
                </span>
                <span style={{ marginLeft: 14, color: "#555", fontSize: 15 }}>
                    &copy; {new Date().getFullYear()} - Developed by OU Team
                </span>
            </Container>
        </footer>
    );
};

export default Footer;
