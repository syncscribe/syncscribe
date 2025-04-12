import {StrictMode} from 'react'
import {createRoot} from 'react-dom/client'
import './index.css'
import {BrowserRouter, Route, Routes} from "react-router-dom";
import Login from "@/pages/auth/Login.tsx";
import DocEditor from "@/pages/DocEditor.tsx";
import ForgotPassword from "@/pages/auth/ForgotPassword.tsx";
import Signup from "@/pages/auth/Signup.tsx";
import { Layout } from './components/Layout';
import Main from './pages/Main.tsx';
import DocView from './pages/DocView.tsx';

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <BrowserRouter>
      <Routes>
        <Route path={"/login"} element={<Login/>}></Route>
        <Route path={"/forgotPassword"} element={<ForgotPassword/>}></Route>
        <Route path={"/signup"} element={<Signup/>}></Route>
        <Route path={"/"} element={<Layout/>}>
          <Route index element={<Main/>}></Route>
          <Route path={"/editor"} element={<DocEditor/>}></Route>
          <Route path={"/documents"} element={<DocView/>}></Route>
          <Route path={"/documents?root=*"} element={<DocView/>}></Route>
          <Route path={"/documents?filter=*"} element={<DocView/>}></Route>
          <Route path={"/documents/labels"} element={<DocView/>}></Route>
        </Route>
      </Routes>
    </BrowserRouter>
  </StrictMode>,
)
