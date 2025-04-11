import {StrictMode} from 'react'
import {createRoot} from 'react-dom/client'
import './index.css'
import {BrowserRouter, Route, Routes} from "react-router-dom";
import Login from "@/pages/auth/Login.tsx";
import DocEditor from "@/pages/DocEditor.tsx";
import ForgotPassword from "@/pages/auth/ForgotPassword.tsx";
import Signup from "@/pages/auth/Signup.tsx";

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <BrowserRouter>
      <Routes>
        <Route path={"/login"} element={<Login/>}></Route>
        <Route path={"/forgotPassword"} element={<ForgotPassword/>}></Route>
        <Route path={"/signup"} element={<Signup/>}></Route>
        <Route path={"/editor"} element={<DocEditor/>}></Route>
      </Routes>
    </BrowserRouter>
  </StrictMode>,
)
