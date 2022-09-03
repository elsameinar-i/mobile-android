#!/usr/bin/env python
# -*- coding: utf-8 -*-

import os
import shutil

project_dir = os.getcwd()

package_dir = '{{ cookiecutter.package_name }}'.replace('.', '/')

os.chdir(os.path.join("app","src","androidTest","java"))
os.makedirs(package_dir)
shutil.move('ApplicationTest.kt', package_dir + '/ApplicationTest.kt')

os.chdir(os.path.join(project_dir,"app","src","main","java"))
os.makedirs(package_dir)


os.chdir(os.path.join(project_dir,"app","src","test","java"))
os.makedirs(package_dir)
shutil.move('ExampleUnitTest.kt', package_dir + '/ExampleUnitTest.kt')

os.chdir(os.path.join(project_dir,"app","src","main","java",package_dir))
os.chdir(os.path.join(project_dir,"app","src","main","java"))

root_dst_dir = os.path.join(project_dir,"app","src","main","java",package_dir)
root_src_dir = os.path.join(project_dir,"app","src","main","java")
root_layout_dir = os.path.join(project_dir,"app","src","main","res","layout")
root_res_dir = os.path.join(project_dir,"app","src","main","res")

if '{{ cookiecutter.splash_screen }}' == 'n':
    shutil.rmtree('ui/splash')

if '{{ cookiecutter.login_page }}' == 'n':
    os.remove(root_layout_dir + "/activity_login.xml")
    os.remove(root_res_dir + "/drawable-hdpi/ic_password.png")
    os.remove(root_res_dir + "/drawable-mdpi/ic_password.png")
    os.remove(root_res_dir + "/drawable-xhdpi/ic_password.png")
    os.remove(root_res_dir + "/drawable-xxhdpi/ic_password.png")
    os.remove(root_res_dir + "/drawable-xxxhdpi/ic_password.png")
    os.remove(root_src_dir + "/data/remote/AuthRemoteDataSource.kt")
    os.remove(root_src_dir + "/data/remote/AuthService.kt")
    os.remove(root_src_dir + "/data/repository/AuthRepository.kt")
    shutil.rmtree('ui/login')

if '{{ cookiecutter.with_menu }}' == 'n':
    shutil.rmtree('ui/main/explore')
    shutil.rmtree('ui/main/profile')
    os.remove(root_layout_dir + "/fragment_explore.xml")
    os.remove(root_layout_dir + "/fragment_profile.xml")
    os.remove(root_res_dir + "/menu/menu_bottomnav_main.xml")
    os.remove(root_res_dir + "/color/bottom_nav_color_click.xml")
    os.remove(root_res_dir + "/drawable-hdpi/ic_explore.png")
    os.remove(root_res_dir + "/drawable-mdpi/ic_explore.png")
    os.remove(root_res_dir + "/drawable-xhdpi/ic_explore.png")
    os.remove(root_res_dir + "/drawable-xxhdpi/ic_explore.png")
    os.remove(root_res_dir + "/drawable-xxxhdpi/ic_explore.png")
    os.remove(root_res_dir + "/drawable-hdpi/ic_beranda.png")
    os.remove(root_res_dir + "/drawable-mdpi/ic_beranda.png")
    os.remove(root_res_dir + "/drawable-xhdpi/ic_beranda.png")
    os.remove(root_res_dir + "/drawable-xxhdpi/ic_beranda.png")
    os.remove(root_res_dir + "/drawable-xxxhdpi/ic_beranda.png")

if '{{ cookiecutter.with_list }}' == 'n' and '{{ cookiecutter.login_page }}' == 'n':
#     Dummy Response digunakan untuk response di halaman login juga
    os.remove(root_src_dir + "/data/remote/model/response/CharacterResponse.kt")
    os.remove(root_src_dir + "/data/local/entities/Character.kt")

if '{{ cookiecutter.with_list }}' == 'n':
    shutil.rmtree('ui/main/characters')
    shutil.rmtree('ui/main/detail')
    os.remove(root_layout_dir + "/fragment_characters.xml")
    os.remove(root_layout_dir + "/fragment_detail_character.xml")
    os.remove(root_layout_dir + "/item_character.xml")
    os.remove(root_src_dir + "/data/local/dao/CharacterDao.kt")
    os.remove(root_src_dir + "/data/local/dao/CharacterRemoteKeysDao.kt")
    os.remove(root_src_dir + "/data/local/entities/CharacterRemoteKeys.kt")
    os.remove(root_src_dir + "/data/paging/CharacterPagingDataSource.kt")
    os.remove(root_src_dir + "/data/paging/CharacterRemoteMediator.kt")
    os.remove(root_src_dir + "/data/remote/model/Info.kt")
    os.remove(root_src_dir + "/data/remote/model/response/CharacterListResponse.kt")
    os.remove(root_src_dir + "/data/remote/CharacterRemoteDataSource.kt")
    os.remove(root_src_dir + "/data/remote/CharacterService.kt")
    os.remove(root_src_dir + "/data/repository/CharacterRepository.kt")
else:
    shutil.rmtree('ui/home')
    os.remove(root_layout_dir + "/fragment_home.xml")

base = os.path.join(root_src_dir,"core")
di = os.path.join(root_src_dir,"di")
utils = os.path.join(root_src_dir,"utils")
ui = os.path.join(root_src_dir,"ui")
data = os.path.join(root_src_dir,"data")

shutil.move('App.kt',os.path.join(project_dir,"app","src","main","java",package_dir,'App.kt'))

def moverecursively(source_folder, destination_folder):
    basename = os.path.basename(source_folder)
    dest_dir = os.path.join(destination_folder, basename)
    if not os.path.exists(dest_dir):
        shutil.move(source_folder, destination_folder)
    else:
        dst_path = os.path.join(destination_folder, basename)
        for root, dirs, files in os.walk(source_folder):
            for item in files:
                src_path = os.path.join(root, item)
                if os.path.exists(dst_file):
                    os.remove(dst_file)
                shutil.move(src_path, dst_path)
            for item in dirs:
                src_path = os.path.join(root, item)
                moverecursively(src_path, dst_path)

moverecursively(base,root_dst_dir)
moverecursively(di,root_dst_dir)
moverecursively(utils,root_dst_dir)
moverecursively(ui,root_dst_dir)
moverecursively(data,root_dst_dir)
