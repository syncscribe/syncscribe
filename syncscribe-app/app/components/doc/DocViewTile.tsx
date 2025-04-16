import {cn} from "@/lib/utils.ts";

export function DocViewTile({
                              className,
                              ...props
                            }: Readonly<React.ComponentPropsWithoutRef<"div">>) {
  return (
    <div className={cn("flex flex-col gap-6", className)} {...props}>
      DocViewTile
    </div>
  );
}