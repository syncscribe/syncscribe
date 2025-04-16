import {cn} from "@/lib/utils.ts";
import {useSelectedRowsStore} from "@/store/useSelectedRowsStore";
import {useEffect} from "react";
import {FolderOutput} from "lucide-react";
import {Button} from "@/components/ui/button.tsx";
import {Tooltip, TooltipContent, TooltipProvider, TooltipTrigger} from "@/components/ui/tooltip.tsx";
import ShareAction from "@/components/actions/ShareAction.tsx";
import TagAction from "@/components/actions/TagAction.tsx";
import DeleteAction from "@/components/actions/DeleteAction.tsx";
import CopyAction from "@/components/actions/CopyAction.tsx";

export default function DocViewAction({
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
          <ShareAction/>
          <TagAction/>
          <DeleteAction/>
          <CopyAction/>
          <TooltipProvider>
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