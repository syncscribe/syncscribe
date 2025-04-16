import {Tooltip, TooltipContent, TooltipProvider, TooltipTrigger} from "@/components/ui/tooltip.tsx";
import {Button} from "@/components/ui/button.tsx";
import {CalendarDays, KeyRound, Pencil, Share} from "lucide-react";
import {
  Dialog,
  DialogClose,
  DialogContent,
  DialogFooter,
  DialogHeader,
  DialogTitle,
  DialogTrigger
} from "@/components/ui/dialog.tsx";
import {Label} from "@/components/ui/label.tsx";
import {Input} from "@/components/ui/input.tsx";
import {useState} from "react";
import {
  Select,
  SelectContent,
  SelectGroup,
  SelectItem,
  SelectLabel,
  SelectTrigger,
  SelectValue
} from "@/components/ui/select.tsx";
import {Popover, PopoverContent, PopoverTrigger} from "@/components/ui/popover.tsx";
import {cn} from "@/lib/utils.ts";
import {Calendar} from "@/components/ui/calendar.tsx";
import {format} from "date-fns";

export default function ShareAction() {
  const [date, setDate] = useState<Date>()

  return (
    <Dialog>
      <DialogTrigger>
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
        </TooltipProvider>
      </DialogTrigger>
      <DialogContent className="sm:max-w-md">
        <DialogHeader>
          <DialogTitle>Share link</DialogTitle>
        </DialogHeader>
        <div className="flex items-center space-x-2">
          <div className="grid flex-1 gap-2">
            <Label htmlFor="link" className="sr-only">
              Link
            </Label>
            <Input
              id="link"
              defaultValue="https://ui.shadcn.com/docs/installation"
              readOnly
            />
          </div>
        </div>
        <div className={"flex flex-col gap-2"}>
          <div className={"text-sm"}>Share settings</div>
          <div className={"flex flex-row gap-2 items-center"}>
            <Pencil strokeWidth={1.5} size={20}/>
            <Select>
              <SelectTrigger className="w-full">
                <SelectValue placeholder="Select share permission"/>
              </SelectTrigger>
              <SelectContent>
                <SelectGroup>
                  <SelectLabel>Permission</SelectLabel>
                  <SelectItem value="read">Can read</SelectItem>
                  <SelectItem value="edit">Can edit</SelectItem>
                </SelectGroup>
              </SelectContent>
            </Select>
          </div>
          <div className={"flex flex-row gap-2 items-center"}>
            <KeyRound strokeWidth={1.5} size={20}/>
            <Input type="password" placeholder={"Create password for your link"}/>
          </div>
          <div className={"flex flex-row gap-2 items-center"}>
            <CalendarDays strokeWidth={1.5} size={20}/>
            <div className={"flex w-full"}>
              <Popover>
                <PopoverTrigger className={"w-full"}>
                  <Button
                    variant={"outline"}
                    className={cn(
                      "w-full justify-start text-left font-normal",
                      !date && "text-muted-foreground"
                    )}>
                    {date ? format(date, "PPP") : <span>Set expiration date</span>}
                  </Button>
                </PopoverTrigger>
                <PopoverContent className="w-auto p-0" align="start">
                  <Calendar
                    mode="single"
                    selected={date}
                    onSelect={setDate}
                    initialFocus
                  />
                </PopoverContent>
              </Popover>
            </div>

          </div>
        </div>
        <DialogFooter className="flex flex-row-reverse">
          <DialogClose asChild>
            <Button type="button">
              Copy Link
            </Button>
          </DialogClose>
        </DialogFooter>
      </DialogContent>
    </Dialog>
  )
}