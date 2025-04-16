import {Navigate, Outlet} from "react-router-dom";

type ProtectedRouteProps = {
  isAuthenticated: boolean | null;
  redirectPath?: string;
}

const ProtectedRoute = ({isAuthenticated, redirectPath = '/login'}: ProtectedRouteProps) => {
  if (!isAuthenticated) {
    return <Navigate to={redirectPath} replace/>;
  }

  return <Outlet/>;
};

export default ProtectedRoute;