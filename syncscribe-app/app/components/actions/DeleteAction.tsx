import {Tooltip, TooltipContent, TooltipProvider, TooltipTrigger} from "@/components/ui/tooltip.tsx";
import {Button} from "@/components/ui/button.tsx";
import {Trash2Icon} from "lucide-react";
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

export default function DeleteAction() {
  return (
    <Dialog>
      <DialogTrigger>
        <TooltipProvider>
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
        </TooltipProvider>
      </DialogTrigger>
      <DialogContent className="w-full">
        <DialogHeader>
          <DialogTitle>Delete documents</DialogTitle>
          <DialogDescription>Do you want to delete your documents? Document will be moved to recycle bin, you have 30 days to recover them.</DialogDescription>
        </DialogHeader>
        <DialogFooter className="flex flex-row-reverse">
          <DialogClose asChild>
            <Button type="button">
              Delete
            </Button>
          </DialogClose>
        </DialogFooter>
      </DialogContent>
    </Dialog>
  )
}