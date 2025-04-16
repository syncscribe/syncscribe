import {Tooltip, TooltipContent, TooltipProvider, TooltipTrigger} from "@/components/ui/tooltip.tsx";
import {Button} from "@/components/ui/button.tsx";
import {Tag} from "lucide-react";
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
import {Select, SelectContent, SelectGroup, SelectItem, SelectTrigger, SelectValue} from "@/components/ui/select.tsx";

const colors = [
  {code: "#000000", name: "Black"},
  {code: "#0042aa", name: "Royal Blue"},
  {code: "#b51a00", name: "Crimson Red"},
  {code: "#ad3e00", name: "Burnt Orange"},
  {code: "#a96800", name: "Amber"},
  {code: "#c4bc00", name: "Chartreuse"},
  {code: "#008cb4", name: "Cerulean"},
  {code: "#7b219f", name: "Royal Purple"},
  {code: "#791a3e", name: "Burgundy"},
  {code: "#4f7a28", name: "Forest Green"},
];

export default function TagAction() {
  return (
    <Dialog>
      <DialogTrigger>
        <TooltipProvider>
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
        </TooltipProvider>
      </DialogTrigger>
      <DialogContent className="w-full">
        <DialogHeader>
          <DialogTitle>Tag your document</DialogTitle>
        </DialogHeader>
        <div className="flex items-center space-x-2">
          <div className="grid flex-1 gap-2">
            <Label htmlFor="tagName" className="sr-only">
              Tag name
            </Label>
            <Input id="tagName" placeholder={"Tag name"}/>
          </div>
          <div>
            <Select>
              <SelectTrigger>
                <SelectValue placeholder={<Tag/>}/>
              </SelectTrigger>
              <SelectContent className={"w-full"}>
                <SelectGroup className={"w-full"}>
                  {colors.map(color => (
                    <SelectItem key={color.code} value={color.code}><Tag color={color.code}/> {color.name}</SelectItem>
                  ))}
                </SelectGroup>
              </SelectContent>
            </Select>
          </div>
        </div>
        <DialogFooter className="flex flex-row-reverse">
          <DialogClose asChild>
            <Button type="button">
              Add tag
            </Button>
          </DialogClose>
        </DialogFooter>
      </DialogContent>
    </Dialog>
  )
}