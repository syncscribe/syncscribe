import {Tooltip, TooltipContent, TooltipProvider, TooltipTrigger} from "@/components/ui/tooltip.tsx";
import {Button} from "@/components/ui/button.tsx";
import {Copy} from "lucide-react";
import {
  Dialog,
  DialogClose,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
  DialogTrigger
} from "@/components/ui/dialog.tsx";

export default function CopyAction() {
  return (
    <Dialog>
      <DialogTrigger>
        <TooltipProvider>
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
        </TooltipProvider>
      </DialogTrigger>
      <DialogContent className="w-full">
        <DialogHeader>
          <DialogTitle>Copy documents</DialogTitle>
          <DialogDescription>Please select copy destination</DialogDescription>
        </DialogHeader>
        <div className="flex items-center space-x-2">
        TBD
        </div>
        <DialogFooter className="flex flex-row-reverse">
          <DialogClose asChild>
            <Button type="button">
              Copy
            </Button>
          </DialogClose>
        </DialogFooter>
      </DialogContent>
    </Dialog>
  )
}