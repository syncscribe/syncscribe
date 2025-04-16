import {cn} from "@/lib/utils.ts"
import {Button} from "@/components/ui/button.tsx"
import {Card, CardContent, CardDescription, CardHeader, CardTitle,} from "@/components/ui/card.tsx"
import {Label} from "@/components/ui/label.tsx"
import {Input} from "@/components/ui/input.tsx"
import {useNavigate} from "react-router-dom";

export function ForgotPasswordForm({
                                     className,
                                     ...props
                                   }: Readonly<React.ComponentPropsWithoutRef<"div">>) {
  const navigate = useNavigate();
  return (
    <div className={cn("flex flex-col gap-6", className)} {...props}>
      <Card>
        <CardHeader>
          <CardTitle className="text-2xl">Forgot password</CardTitle>
          <CardDescription>
            Enter your email below to reset your password
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
              <Button type="submit" className="w-full cursor-pointer">
                Submit
              </Button>
            </div>
            <div className="mt-4 text-center text-sm">
              Don&apos;t have an account?{" "}
              <Button variant={"link"}
                      className="cursor-pointer p-0"
                      onClick={() => navigate("/signup")}>
                Sign up
              </Button>
            </div>
            <div className="text-center text-sm">
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
