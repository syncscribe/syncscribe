import {Outlet} from "react-router-dom"
import {AppSidebar} from "@/components/nav/AppSidebar.tsx";
import {SidebarInset, SidebarProvider, SidebarTrigger} from "@/components/ui/sidebar.tsx";
import {Separator} from "@/components/ui/separator.tsx";
import {Breadcrumb, BreadcrumbItem, BreadcrumbLink, BreadcrumbList} from "@/components/ui/breadcrumb.tsx";
import DocViewAction from "@/components/doc/DocViewAction.tsx";
import DocViewType from "@/components/doc/DocViewType.tsx";

type LayoutProps = {
  signout: () => void;
}

export const Layout = ({signout}: LayoutProps) => {
  return (
    <SidebarProvider>
      <AppSidebar signout={signout}/>
      <SidebarInset>
        <header
          className="flex h-16 shrink-0 items-center gap-2 transition-[width,height] ease-linear group-has-[[data-collapsible=icon]]/sidebar-wrapper:h-12">
          <div className="flex items-center gap-2 px-4">
            <SidebarTrigger className="-ml-1"/>
            <Separator orientation="vertical" className="mr-2 h-4"/>
            <Breadcrumb>
              <BreadcrumbList>
                <BreadcrumbItem className="hidden md:block">
                  <BreadcrumbLink href="#">
                    Home
                  </BreadcrumbLink>
                </BreadcrumbItem>
              </BreadcrumbList>
            </Breadcrumb>
          </div>
        </header>
        <div className="flex flex-1 flex-col gap-4 p-4 pt-0">
          <div className={"flex flex-row items-center"}>
            <DocViewAction/>
            <DocViewType className={"ml-auto"}/>
          </div>
          <Outlet/>
        </div>
      </SidebarInset>
    </SidebarProvider>
  )
}