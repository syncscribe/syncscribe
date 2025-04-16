import {createZitadelAuth, ZitadelConfig} from "@zitadel/react";
import {useEffect, useState} from "react";
import {Navigate, Route, Routes} from "react-router-dom";
import Login from "@/pages/auth/Login.tsx";
import ForgotPassword from "@/pages/auth/ForgotPassword.tsx";
import Signup from "@/pages/auth/Signup.tsx";
import {Layout} from "@/components/Layout.tsx";
import Main from "@/pages/Main.tsx";
import DocEditor from "@/pages/DocEditor.tsx";
import DocView from "@/pages/DocView.tsx";
import Callback from "@/pages/auth/Callback.tsx";
import ProtectedRoute from "@/components/auth/ProtectedRoute.tsx";

function App() {
  const config: ZitadelConfig = {
    authority: "http://localhost:8080/",
    client_id: "315832230725353476",
    redirect_uri: "http://localhost:5173/callback",
    response_type: 'code',
    scope: 'openid profile email',
    post_logout_redirect_uri: "http://localhost:5173/",
    response_mode: "query",
  }
  const zitadel = createZitadelAuth(config);

  const signout = () => {
    zitadel.signout();
  }

  const signin = () => {
    zitadel.authorize();
  }

  const [authenticated, setAuthenticated] = useState<boolean | null>(null);

  useEffect(() => {
    zitadel.userManager.getUser().then((user) => {
      if (user) {
        setAuthenticated(true);
      } else {
        setAuthenticated(false);
      }
    })
  }, [zitadel]);

  return (
    <Routes>
      <Route path="/" element={<Navigate to={"/login"}/>}></Route>
      <Route path="/callback" element={
        <Callback
          authenticated={authenticated}
          setAuth={setAuthenticated}
          userManager={zitadel.userManager}
        />
      }/>
      <Route path={"/login"} element={<Login login={signin}/>}></Route>
      <Route path={"/forgotPassword"} element={<ForgotPassword/>}></Route>
      <Route path={"/signup"} element={<Signup/>}></Route>
      <Route element={<ProtectedRoute isAuthenticated={authenticated}/>}>
        <Route path={"/home"} element={<Layout signout={signout}/>}>
          <Route index element={<Main/>}></Route>
          <Route path={"editor"} element={<DocEditor/>}></Route>
          <Route path={"documents"} element={<DocView/>}></Route>
          <Route path={"documents?root=*"} element={<DocView/>}></Route>
          <Route path={"documents?filter=*"} element={<DocView/>}></Route>
          <Route path={"documents/labels"} element={<DocView/>}></Route>
        </Route>
      </Route>
    </Routes>
  )
}

export default App;