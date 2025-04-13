import {Input} from "@/components/ui/input.tsx";
import {ToggleGroup, ToggleGroupItem} from "@/components/ui/toggle-group.tsx";
import {LayoutGrid, List} from "lucide-react";
import {Tooltip, TooltipContent, TooltipProvider, TooltipTrigger} from "@/components/ui/tooltip.tsx";
import {cn} from "@/lib/utils.ts";
import {Button} from "@/components/ui/button.tsx";

export default function DocViewType({
                                        className,
                                        ...props
                                      }: Readonly<React.ComponentPropsWithoutRef<"div">>) {
  return (
    <div className={cn("flex flex-row items-center gap-2", className)} {...props}>
      <TooltipProvider>
        <ToggleGroup type="single">
          <ToggleGroupItem value="list">
            <Tooltip>
              <TooltipTrigger>
                <Button variant="ghost" size="icon" className={"cursor-pointer"}>
                  <List/>
                </Button>
              </TooltipTrigger>
              <TooltipContent>
                View as list
              </TooltipContent>
            </Tooltip>
          </ToggleGroupItem>
          <ToggleGroupItem value="icons">
            <Tooltip>
              <TooltipTrigger>
                <Button variant="ghost" size="icon" className={"cursor-pointer"}>
                  <LayoutGrid/>
                </Button>
              </TooltipTrigger>
              <TooltipContent>
                View as tiles
              </TooltipContent>
            </Tooltip>
          </ToggleGroupItem>
        </ToggleGroup>
      </TooltipProvider>
      <Input type="text" placeholder="Search..."/>
    </div>
  )
}