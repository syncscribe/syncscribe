import {LoginForm} from "@/components/auth/LoginForm.tsx";
import {GalleryVerticalEnd} from "lucide-react";
import {Button} from "@/components/ui/button.tsx";
import {useNavigate} from "react-router-dom";

const LoginPage = () => {
  const navigate = useNavigate();

  return (
    <div className="flex min-h-svh flex-col items-center justify-center gap-6 bg-muted p-6 md:p-10">
      <div className="flex w-full max-w-sm flex-col gap-6">
        <Button variant={"link"}
                className="cursor-pointer font-medium decoration-0 text-xl"
                onClick={() => navigate("/")}>
          <div className="flex h-6 w-6 items-center justify-center rounded-md bg-primary text-primary-foreground">
            <GalleryVerticalEnd className="size-4"/>
          </div>
          SyncScribe.io
        </Button>
        <LoginForm />
      </div>
    </div>
  );
}

export default LoginPage;