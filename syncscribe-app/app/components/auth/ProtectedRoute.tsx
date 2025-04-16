import {Navigate, Outlet} from "react-router-dom";
import {useAuthUserStore} from "@/store/useAuthUserStore.ts";

type ProtectedRouteProps = {
  redirectPath?: string;
}

const ProtectedRoute = ({redirectPath = '/login'}: ProtectedRouteProps) => {
  const user = useAuthUserStore(state => state.user)
  console.log("ProtectedRoute: ", user)
  if (!user) {
    return <Navigate to={redirectPath} replace/>;
  }

  return <Outlet/>;
};

export default ProtectedRoute;