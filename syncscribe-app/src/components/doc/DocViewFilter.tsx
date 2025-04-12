import {cn} from "@/lib/utils.ts";
import {useSelectedRowsStore} from "@/store/useSelectedRowsStore";
import {useEffect} from "react";
import {Copy, FolderOutput, Share, Tag, Trash2Icon} from "lucide-react";
import {Button} from "@/components/ui/button.tsx";
import {Tooltip, TooltipContent, TooltipProvider, TooltipTrigger} from "@/components/ui/tooltip.tsx";

export default function DocViewFilter({
                                        className,
                                        ...props
                                      }: Readonly<React.ComponentPropsWithoutRef<"div">>) {
  const selectedRows = useSelectedRowsStore((state) => state.selectedRows);
  const clearSelectedRows = useSelectedRowsStore((state) => state.clearSelectedRows);

  useEffect(() => {
    clearSelectedRows();
  }, [clearSelectedRows]);

  return (
    <div className={cn("flex flex-col gap-2", className)} {...props}>
      {selectedRows.length > 0 &&
        <div className={"flex flex-row gap-3 items-center"}>
          <TooltipProvider>
            <Tooltip>
              <TooltipTrigger>
                <Button variant="ghost" size="icon" className={"cursor-pointer"}>
                  <Share/>
                </Button>
              </TooltipTrigger>
              <TooltipContent>
                Share
              </TooltipContent>
            </Tooltip>
            <Tooltip>
              <TooltipTrigger>
                <Button variant="ghost" size="icon" className={"cursor-pointer"}>
                  <Tag/>
                </Button>
              </TooltipTrigger>
              <TooltipContent>
                Tag
              </TooltipContent>
            </Tooltip>
            <Tooltip>
              <TooltipTrigger>
                <Button variant="ghost" size="icon" className={"cursor-pointer"}>
                  <Trash2Icon/>
                </Button>
              </TooltipTrigger>
              <TooltipContent>
                Delete
              </TooltipContent>
            </Tooltip>
            <Tooltip>
              <TooltipTrigger>
                <Button variant="ghost" size="icon" className={"cursor-pointer"}>
                  <Copy/>
                </Button>
              </TooltipTrigger>
              <TooltipContent>
                Copy
              </TooltipContent>
            </Tooltip>
            <Tooltip>
              <TooltipTrigger>
                <Button variant="ghost" size="icon" className={"cursor-pointer"}>
                  <FolderOutput/>
                </Button>
              </TooltipTrigger>
              <TooltipContent>
                Move
              </TooltipContent>
            </Tooltip>
          </TooltipProvider>
          <div className={"flex flex-row text-sm "}>
            {selectedRows.length + " selected"}
          </div>
        </div>
      }
    </div>
  )
}