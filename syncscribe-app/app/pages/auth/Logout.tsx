import { Button } from "@/components/ui/button.tsx";
import { useAuthUserStore } from "@/store/useAuthUserStore";
import { GalleryVerticalEnd } from "lucide-react";
import { useEffect } from "react";
import { useNavigate } from "react-router-dom";

type LogoutProps = {
  logout: () => void;
};

const LogoutPage = ({ logout }: LogoutProps) => {
  const navigate = useNavigate();
  const clearUser = useAuthUserStore((state) => state.clearUser);

  useEffect(() => {
    // Only clear user and call logout, do not navigate
    logout();
    clearUser();
    // No manual navigation, let Zitadel handle redirect
  }, [logout, clearUser]);

  return (
    <div className="flex min-h-svh flex-col items-center justify-center gap-6 bg-muted p-6 md:p-10">
      <div className="flex w-full max-w-sm flex-col gap-6">
        <Button
          variant={"link"}
          className="cursor-pointer font-medium decoration-0 text-xl"
          onClick={() => navigate("/")}
        >
          <div className="flex h-6 w-6 items-center justify-center rounded-md bg-primary text-primary-foreground">
            <GalleryVerticalEnd className="size-4" />
          </div>
          SyncScribe.io
        </Button>
        <div className={"flex flex-col items-center justify-center"}>
          Logging out...
        </div>
      </div>
    </div>
  );
};

export default LogoutPage;
