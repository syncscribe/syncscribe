import {useEffect, useState} from "react";
import {UserManager, User} from "oidc-client-ts";
import {Navigate} from "react-router-dom";

type Props = {
  authenticated: boolean | null;
  setAuth: (authenticated: boolean | null) => void;
  userManager: UserManager;
};

const Callback = ({
                    authenticated,
                    setAuth,
                    userManager,
                  }: Props) => {
  const [userInfo, setUserInfo] = useState<User | null>(null);

  useEffect(() => {
    async function checkAuth() {
      if (authenticated === null) {
        const user = await userManager.signinRedirectCallback();
        setAuth(!!user);
        setUserInfo(user);
      }
    }
    checkAuth();
  }, [authenticated, userManager, setAuth]);
  if (authenticated === true && userInfo) {
    return (
      <Navigate to={"/home"}></Navigate>
    );
  } else {
    return <div>Loading...</div>;
  }
};

export default Callback;