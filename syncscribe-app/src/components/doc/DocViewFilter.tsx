import {cn} from "@/lib/utils.ts";

interface DocViewFilterProps extends React.ComponentPropsWithoutRef<"div"> {
  selectedRows?: any[];
}

export default function DocViewFilter({
                                        className,
                                        selectedRows = [],
                                        ...props
                                      }: Readonly<DocViewFilterProps>) {
  return (
    <div className={cn("flex flex-row items-center", className)} {...props}>
      {selectedRows.length > 0 ? `${selectedRows.length} items selected` : "No items selected"}
    </div>
  )
}