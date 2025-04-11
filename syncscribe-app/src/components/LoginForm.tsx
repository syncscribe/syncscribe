import {cn} from "@/lib/utils"
import {Button} from "@/components/ui/button"
import {Card, CardContent, CardDescription, CardHeader, CardTitle,} from "@/components/ui/card"
import {Label} from "@/components/ui/label"
import {Input} from "@/components/ui/input"
import {useNavigate} from "react-router-dom";

export function LoginForm({
                            className,
                            ...props
                          }: Readonly<React.ComponentPropsWithoutRef<"div">>) {
  const navigate = useNavigate();
  return (
    <div className={cn("flex flex-col gap-6", className)} {...props}>
      <Card>
        <CardHeader>
          <CardTitle className="text-2xl">Welcome back</CardTitle>
          <CardDescription>
            Enter your email below to login to your account
          </CardDescription>
        </CardHeader>
        <CardContent>
          <form>
            <div className="flex flex-col gap-6">
              <div className="grid gap-2">
                <Label htmlFor="email">Email</Label>
                <Input
                  id="email"
                  type="email"
                  placeholder="m@example.com"
                  required
                />
              </div>
              <div className="grid gap-2">
                <div className="flex items-center">
                  <Label htmlFor="password">Password</Label>
                  <Button variant={"link"}
                      className="cursor-pointer ml-auto"
                      onClick={() => navigate("/forgotPassword")}>
                    Forgot your password?
                  </Button>
                </div>
                <Input id="password" type="password" required/>
              </div>
              <Button type="submit" className="w-full cursor-pointer">
                Login
              </Button>
               <div className="relative text-center text-sm after:absolute after:inset-0 after:top-1/2 after:z-0 after:flex after:items-center after:border-t after:border-border">
                <span className="relative z-10 bg-background px-2 text-muted-foreground">
                  Or continue with
                </span>
              </div>
              <div className={"flex flex-col gap-2"}>
                <Button variant={"outline"} className="w-full border-red-600 cursor-pointer hover:bg-red-100">
                  Login with Google
                </Button>
                <Button variant={"outline"} className="w-full border-sky-700 cursor-pointer hover:bg-sky-100">
                  Login with Microsoft
                </Button>
              </div>
            </div>
            <div className="mt-4 text-center text-sm">
              Don&apos;t have an account?{" "}
              <Button variant={"link"}
                      className="cursor-pointer p-0"
                      onClick={() => navigate("/signup")}>
                Sign up
              </Button>
            </div>
          </form>
        </CardContent>
      </Card>
    </div>
  )
}
