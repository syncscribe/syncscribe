import {CloudAlert} from "lucide-react";
import {Button} from "@/components/ui/button.tsx";
import {useNavigate} from "react-router-dom";

export default function NotFound() {
  const navigate = useNavigate();
  return (
    <div className={"w-full h-[100vh] flex flex-col items-center justify-center"}>
      <div className={"flex flex-col items-center justify-center gap-5"}>
        <CloudAlert size={48}/>
        <div className={"text-xl"}>Ooops!! This page does not exist</div>
        <Button variant={"outline"} onClick={() => navigate("/home")}>
          Go back
        </Button>
      </div>
    </div>
  )
}