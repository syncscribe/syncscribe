import {ForgotPasswordForm} from "@/components/ForgotPasswordForm.tsx";

const ForgotPasswordPage = () => {
  return (
    <div className="flex min-h-svh w-full items-center justify-center p-6 md:p-10">
      <div className="w-full max-w-sm">
        <ForgotPasswordForm />
      </div>
    </div>
  );
}

export default ForgotPasswordPage;