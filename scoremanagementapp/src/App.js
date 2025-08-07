import { BrowserRouter, Route, Routes } from "react-router-dom";
import Footer from "./components/layout/Footer";
import Header from "./components/layout/Header";
import Home from "./components/Home";
import 'bootstrap/dist/css/bootstrap.min.css';
import Register from "./components/Register";
import Login from "./components/Login";
import Profile from "./components/Profile";
import StudentList from "./components/Teacher/StudentList";
import AddScore from "./components/Teacher/AddScore";
import SubjectList from "./components/Student/SubjectList";
import MyScore from "./components/Student/MyScore";
import MyClasses from "./components/Student/MyClasses";
import { MyDispatchContext, MyUserContext } from "./configs/MyContexts";
import { useReducer } from "react";
import MyUserReducer from "./reducers/MyUserReducer";
import { Navigate } from "react-router-dom";
import RegisterClass from "./components/Student/RegisterClass";

const App = () => {
  const [user, dispatch] = useReducer(MyUserReducer, null);

  return (
    <MyUserContext.Provider value={user}>
      <MyDispatchContext.Provider value={dispatch}>
        <BrowserRouter>
          <Header />
          <Routes>
            <Route path="/register" element={<Register />} />
            <Route path="/home" element={<Home />} />
            <Route path="/studentlist/:classId" element={<StudentList />} />
            <Route path="/addscore/:classSubjectId" element={<AddScore />} />
            <Route path="/login" element={<Login />} />
            <Route path="/profile" element={<Profile />} />
            <Route path="/" element={<Navigate to="/login" />} />
            <Route path="/myscore/:classSubjectId" element={<MyScore />} />
            <Route path="/myclasses" element={<MyClasses />} />
            <Route path="/registerclass" element={<RegisterClass />} />
            {/* <Route path="/forumlist" element={<ForumList />} /> */}
            <Route path="/subjectlist" element={<SubjectList />} />
        </Routes>
        <Footer />
      </BrowserRouter>
    </MyDispatchContext.Provider>
    </MyUserContext.Provider >
  );
}
export default App;