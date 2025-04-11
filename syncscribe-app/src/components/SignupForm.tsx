import {cn} from "@/lib/utils"
import {Button} from "@/components/ui/button"
import {Card, CardContent, CardDescription, CardHeader, CardTitle,} from "@/components/ui/card"
import {Label} from "@/components/ui/label"
import {Input} from "@/components/ui/input"
import {useNavigate} from "react-router-dom";

export function SignupForm({
                            className,
                            ...props
                          }: Readonly<React.ComponentPropsWithoutRef<"div">>) {
  const navigate = useNavigate();
  return (
    <div className={cn("flex flex-col gap-6", className)} {...props}>
      <Card>
        <CardHeader>
          <CardTitle className="text-2xl">Signup</CardTitle>
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
                </div>
                <Input id="password" type="password" required/>
              </div>
              <div className="grid gap-2">
                <div className="flex items-center">
                  <Label htmlFor="confirmPassword">Confirm password</Label>
                </div>
                <Input id="confirmPassword" type="password" required/>
              </div>
              <Button type="submit" className="w-full cursor-pointer">
                Create account
              </Button>
            </div>
            <div className="mt-4 text-center text-sm">
              Already have an account?{" "}
              <Button variant={"link"}
                      className="cursor-pointer p-0"
                      onClick={() => navigate("/login")}>
                Login
              </Button>
            </div>
          </form>
        </CardContent>
      </Card>
    </div>
  )
}
